package com.tensquare.asset.service;

import com.tensquare.asset.pojo.DampUserPojo;
import entity.ApiRequest;
import entity.ApiResponse;

import java.util.List;

public interface IUser {

    /**
      * @auther ZPF
      * @description: 查询所有
      * @date 2020/3/24
      * @return  entity.ApiResponse<java.util.List<com.tensquare.asset.pojo.DampUserPojo>>
      */
    ApiResponse<List<DampUserPojo>> findAll();



    /*
      * @auther ZPF
      * @description: 根据id查询用户详情页
      * @date 2020/3/24
      * @return  entity.ApiResponse<com.tensquare.asset.pojo.DampUserPojo>
      */
    ApiResponse<DampUserPojo> findById(ApiRequest<String> req);
}
