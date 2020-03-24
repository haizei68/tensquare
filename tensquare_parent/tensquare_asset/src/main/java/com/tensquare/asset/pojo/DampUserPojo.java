package com.tensquare.asset.pojo;

import lombok.Data;


import java.util.Date;

@Data
public class DampUserPojo {

     private  String id;

     private  String name;

     private  Date createTime;

     private  Date updateTime;

     private  String updateBy;

     private  String status;

     private  String password;
}
