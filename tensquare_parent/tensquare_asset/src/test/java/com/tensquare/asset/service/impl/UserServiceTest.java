package com.tensquare.asset.service.impl;

import com.tensquare.asset.AssetApplication;
import com.tensquare.asset.pojo.DampUserPojo;
import com.tensquare.asset.service.IUser;
import entity.ApiResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AssetApplication.class)
public class UserServiceTest {
    @Resource
    private IUser userService;

    @Test
    public void  findAll(){
        ApiResponse<List<DampUserPojo>> resp = userService.findAll();
        Assert.assertTrue(ApiResponse.SUCCESS.equals(resp.getResultCode()));
    }
}