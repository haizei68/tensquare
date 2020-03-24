package com.tensquare.asset.service.impl;

import com.tensquare.asset.dao.DampUserDao;
import com.tensquare.asset.pojo.DampUserPojo;
import com.tensquare.asset.service.IUser;
import entity.ApiRequest;
import entity.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements IUser {

    @Resource
    private DampUserDao userDao;

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
    public ApiResponse<DampUserPojo> findById(ApiRequest<String> req) {
        ApiResponse<DampUserPojo> resp = new ApiResponse<>();
        if (StringUtils.isNotEmpty(req.getBizParam())){
            resp.setResultObj(userDao.findById(req.getBizParam()));
        }
        resp.setResultCode(ApiResponse.SUCCESS);
        resp.setResultMsg("查询成功");
        return resp;
    }

}
