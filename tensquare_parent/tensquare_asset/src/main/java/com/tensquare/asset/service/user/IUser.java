package com.tensquare.asset.service.user;

import com.tensquare.asset.entity.ApiRequest;
import com.tensquare.asset.entity.ApiResponse;
import com.tensquare.asset.pojo.DampUserPojo;

import java.util.List;

public interface IUser {

    /**
      * @auther ZPF
      * @description: 查询所有
      * @date 2020/3/24
      * @return  entity.ApiResponse<java.util.List<com.tensquare.asset.pojo.UserPojo>>
      */
    ApiResponse<List<DampUserPojo>> findAll();



    /*
      * @auther ZPF
      * @description: 根据id查询用户详情页
      * @date 2020/3/24
      * @return  entity.ApiResponse<com.tensquare.asset.pojo.UserPojo>
      */
    ApiResponse<DampUserPojo> findById(ApiRequest<String> req);
}
