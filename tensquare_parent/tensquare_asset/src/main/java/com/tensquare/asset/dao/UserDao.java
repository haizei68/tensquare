package com.tensquare.asset.dao;

import com.tensquare.asset.pojo.DampUserPojo;

import java.util.List;

public interface UserDao {

   /**
    * 查询所有
    * @return
    */
   List<DampUserPojo> findAll();

   /**
    * 根据id查询详情页
    * @param userId
    * @return
    */
   DampUserPojo findById(String userId);

    DampUserPojo getUserInfoById(String userId);
}
