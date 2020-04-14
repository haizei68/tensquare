package com.tensquare.asset.entity;

/**
 * API请求令牌
 *
 */
public class ApiToken {
    /**
     * 调用方应用系统id
     */
    private String sysId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 到期时间(yyyyMMddHHmmss)
     */
    private String expireTime;

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }



}
