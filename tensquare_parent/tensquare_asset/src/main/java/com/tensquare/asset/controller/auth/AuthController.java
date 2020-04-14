package com.tensquare.asset.controller.auth;

import com.tensquare.asset.dto.UserInfo;
import com.tensquare.asset.entity.ApiRequest;
import com.tensquare.asset.entity.ApiResponse;
import com.tensquare.asset.service.auth.IAuth;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Resource
    private IAuth authService;

    /**
     * 通过damp的登录页面登录时调用
     * @param req
     * @return
     */
    @RequestMapping(path = "login", method = RequestMethod.POST)
    public ApiResponse<UserInfo> login(
            @RequestBody ApiRequest<Void> req) {
        return authService.login(req);
    }

    /**
     * 通过eip单点登录时调用
     * @param req
     * @return
     */
    @RequestMapping(path = "loginFromEip", method = RequestMethod.POST)
    public ApiResponse<UserInfo> loginFromEip(@RequestBody ApiRequest<Void> req) {
        return authService.loginFromEip(req);
    }

    /**
     * 刷新令牌
     * @param req
     * @return
     */
    @RequestMapping(path = "refreshToken", method = RequestMethod.POST)
    public  ApiResponse<String> refreshToken(ApiRequest<Void> req) {
        return authService.refreshToken(req);
    }

    /**
     * 通过token获取用户信息
     * @param req
     * @return
     */
    @RequestMapping(path = "getUserInfoByToken", method = RequestMethod.POST)
    public ApiResponse<UserInfo> getUserInfoByToken(@RequestBody ApiRequest<Void> req){
        return authService.getUserInfoByToken(req);
    }

}