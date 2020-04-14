package com.tensquare.asset.service.auth.impl;

import com.tensquare.asset.dao.UserDao;
import com.tensquare.asset.dto.UserInfo;
import com.tensquare.asset.entity.ApiRequest;
import com.tensquare.asset.entity.ApiResponse;
import com.tensquare.asset.entity.ApiToken;
import com.tensquare.asset.entity.TokenGenerator;
import com.tensquare.asset.log.DcmsLogTrace;
import com.tensquare.asset.pojo.DampUserPojo;
import com.tensquare.asset.service.auth.IAuth;
import com.tensquare.constant.AuthConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * Api权限相关服务
 *
 * @author
 **/
@Service("authService")
public class AuthService implements IAuth {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public static final String TOURIST_ID = "tourist001";//普通用户ID

    @Resource
    private UserDao userDao;

    @Override
    @DcmsLogTrace(checkApiRequest = true, checkSysId = true, checkUserId = true, checkBizParam = false)
    public ApiResponse<UserInfo> login(ApiRequest<Void> req) {
        ApiResponse<UserInfo> resp = new ApiResponse<>();
        String sysId = req.getSysId();
        if (!AuthConstant.isSupportedSys(sysId)) {
            resp.setResultCode(ApiResponse.AUTH_FAIL);
            resp.setResultMsg("未认证的调用方");
            return resp;
        }
        return getUserInfo(req, true, true);
    }

    @Override
    @DcmsLogTrace(checkApiRequest = true, checkSysId = true, checkUserId = true,
            checkToken = true, checkBizParam = false)
    public ApiResponse<String> refreshToken(ApiRequest<Void> req) {
        ApiResponse<String> resp = new ApiResponse<String>();
        ApiResponse<Void> chkToken = validateToken(req);
        if (!ApiResponse.SUCCESS.equals(chkToken.getResultCode())) {
            resp.setResultCode(chkToken.getResultCode());
            resp.setResultMsg(chkToken.getResultMsg());
            return resp;
        }
        String token = TokenGenerator.generateToken(req.getSysId(), req.getUserId(),
              "asset");
        resp.setResultCode(ApiResponse.SUCCESS);
        resp.setResultObj(token);
        return resp;

    }

    /*
     * 令牌校验
     * @param req
     * @return
     */
    @DcmsLogTrace(checkApiRequest = true, checkSysId = true, checkUserId = true, checkToken = true, checkBizParam =
            false)
    public ApiResponse<Void> validateToken(ApiRequest<Void> req) {
        ApiResponse<Void> resp = new ApiResponse<Void>();
        String sysId = req.getSysId();
        if (!AuthConstant.isSupportedSys(sysId)) {
            resp.setResultCode(ApiResponse.AUTH_FAIL);
            resp.setResultMsg("未认证的调用方");
            return resp;
        }
        //1解密旧token
        ApiToken oldToken = null;
        try {
            oldToken = TokenGenerator.decryptToken(req.getSysId(), req.getToken(), req.getUserId(),
                  "asset");
        } catch (Exception ex) {
            LOGGER.error("decryptToken fail", ex);
            resp.setResultCode(ApiResponse.AUTH_FAIL);
            resp.setResultMsg("令牌校验失败");
            return resp;
        }
        //2系统-用户校验
        if (!req.getSysId().equals(oldToken.getSysId())) {
            resp.setResultCode(ApiResponse.AUTH_FAIL);
            resp.setResultMsg("令牌校验失败,sysId不一致");
            return resp;
        }
        if (!req.getUserId().equals(oldToken.getUserId())) {
            resp.setResultCode(ApiResponse.AUTH_FAIL);
            resp.setResultMsg("令牌校验失败,userId不一致");
            return resp;
        }
        //3旧令牌过期校验
        if (TokenGenerator.isExpired(oldToken)) {
            resp.setResultCode(ApiResponse.TOKEN_EXPIRED);
            resp.setResultMsg("令牌校验失败,已过期");
            return resp;
        }
        resp.setResultCode(ApiResponse.SUCCESS);
        return resp;
    }

    @Override
    public ApiResponse<UserInfo> loginFromEip(ApiRequest<Void> req) {
        ApiResponse<UserInfo> resp = new ApiResponse<>();
//        String sysId = req.getSysId();
//        if (!AuthConstant.isSupportedSys(sysId)) {
//            resp.setResultCode(ApiResponse.AUTH_FAIL);
//            resp.setResultMsg("未认证的调用方");
//            return resp;
//        }
        return getUserInfo(req, false, false);
    }

    /**
     * 如果是通过eip单点登录则不校验密码
     *
     * @param req
     * @param checkPassword
     * @return
     */
    private ApiResponse<UserInfo> getUserInfo(ApiRequest<Void> req, boolean checkPassword, boolean isLogin) {
        /*效验本地登录密码*/
        ApiResponse<UserInfo> resp = new ApiResponse<>();
        String userId = req.getUserId();
        String password = req.getPassword();
        //TODO:目前通过EIP来的请求都不校验密码。前端服务器和eip有IP白名单限制
        if ((checkPassword) &&
                (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password))) {
            resp.setResultCode(ApiResponse.AUTH_FAIL);
            resp.setResultMsg("用户账号密码不能为空");
            return resp;
        }
        //查询该用户在damp_user表存在不,如果不存在,则去elt_hr_user表查询有无该用户,有就添加到damp_user表,
        //没有则是游客
//        Boolean addTrue = EltUserUtils.autoAddUserIntoUserDampTab(userId);
        DampUserPojo userPojo=null;
        //if (addTrue){
            userPojo = userDao.getUserInfoById(userId);
            if ("0".equals(userPojo.getStatus())){
                resp.setResultCode(ApiResponse.AUTH_FAIL);
                resp.setResultMsg("账户已冻结，请联系管理员！");
                return resp;
            }
            if (checkPassword) {
                //String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
               // if (!md5.equals(userPojo.getPassword())) {
                if (!password.equals(userPojo.getPassword())) {
                    resp.setResultCode(ApiResponse.AUTH_FAIL);
                    resp.setResultMsg("用户账号密码错误");
                    return resp;
                }
            }

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userPojo, userInfo);
        /*查询当前用户包含权限点，是否超级用户*/
//        Boolean admin = UserUtils.isAdmin(userId);
//        List<String> userPermissionPoints = UserUtils.getUserPermissionPoints(userId);
//        userInfo.setAdmin(admin);
//        userInfo.setPermissionPoints(userPermissionPoints);
        /*查询用户团队*/
//        List<UserInfo> userOrganizations = userDao.getUserOrganization(userId);
//        if (!CollectionUtils.isEmpty(userOrganizations)) {
//            userInfo.setOrganizationName(userOrganizations.get(0).getOrganizationName());
//        }
        String token = TokenGenerator.generateToken(req.getSysId(), userId, "damp"
                ,172800000);
        userInfo.setToken(token);
        userInfo.setLogin(isLogin);
        resp.setResultCode(ApiResponse.SUCCESS);
        resp.setResultObj(userInfo);
        return resp;
    }

    @Override
    @DcmsLogTrace(checkApiRequest = true, checkSysId = true, checkUserId = false,
            checkToken = true, checkBizParam = false)
    public ApiResponse<UserInfo> getUserInfoByToken(ApiRequest<Void> req) {
        ApiResponse<UserInfo> resp = new ApiResponse<UserInfo>();
//        ApiToken tk = null;
//        try {
//            tk = TokenGenerator.decryptToken(req.getSysId(), req.getToken(), null,
//                  "asset");
//        } catch (Exception ex) {
//            LOGGER.error("decryptToken fail", ex);
//            resp.setResultCode(ApiResponse.AUTH_FAIL);
//            resp.setResultMsg("令牌校验失败");
//            return resp;
//        }
//        //2系统校验
//        if (!req.getSysId().equals(tk.getSysId())) {
//            resp.setResultCode(ApiResponse.AUTH_FAIL);
//            resp.setResultMsg("令牌校验失败,sysId不一致");
//            return resp;
//        }
//        req.setUserId(tk.getUserId());
        return getUserInfo(req, false, false);
    }

}

