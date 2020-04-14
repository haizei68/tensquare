package com.tensquare.asset.service.user.impl;

import com.tensquare.asset.dao.UserDao;
import com.tensquare.asset.entity.ApiRequest;
import com.tensquare.asset.entity.ApiResponse;
import com.tensquare.asset.log.DcmsLogTrace;
import com.tensquare.asset.pojo.DampUserPojo;
import com.tensquare.asset.service.user.IUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements IUser {

    private final static Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserDao userDao;

    @Override
    public ApiResponse<List<DampUserPojo>> findAll() {
        ApiResponse<List<DampUserPojo>> resp = new ApiResponse<>();
        List<DampUserPojo> data = userDao.findAll();
        resp.setResultObj(data);
        resp.setResultCode(ApiResponse.SUCCESS);
        resp.setResultMsg("查询成功");
        return resp;
    }

    @Override
    @DcmsLogTrace
    public ApiResponse<DampUserPojo> findById(ApiRequest<String> req) {
        ApiResponse<DampUserPojo> resp = new ApiResponse<>();
        if (StringUtils.isNotEmpty(req.getBizParam())){
            resp.setResultObj(userDao.findById(req.getBizParam()));
        }
        LOGGER.info(resp.getResultObj().toString());
        resp.setResultCode(ApiResponse.SUCCESS);
        resp.setResultMsg("查询成功");
        return resp;
    }

}
