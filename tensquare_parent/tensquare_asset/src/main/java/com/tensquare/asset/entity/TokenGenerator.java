package com.tensquare.asset.entity;

import com.alibaba.fastjson.JSON;
import com.tensquare.asset.utils.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 令牌生成器
 *
 *
 */
public class TokenGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenGenerator.class);

    private static final long MILLS30MIN=30*60*1000;

    /**
     * 创建令牌-过期时间为30分钟后
     *
     * @param sysId
     * @param userId
     * @param secret
     * @return
     */
    public static String generateToken(String sysId, String userId, String secret) {
        return generateToken(sysId,userId,secret,MILLS30MIN);
    }

    public static String generateToken(String sysId, String userId, String secret,long exipremills) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ApiToken token = new ApiToken();
        token.setSysId(sysId);
        token.setUserId(userId);
        Date expireTime=new Date(System.currentTimeMillis()+exipremills);
        token.setExpireTime(sdf.format(expireTime));
        return AESUtil.encrypt(JSON.toJSONString(token), secret);
    }

    /**
     * 解密令牌
     *
     * @param tokenString
     * @param secret
     * @return
     */
    public static ApiToken decryptToken(String sysId,String tokenString, String userId,String secret) {
        String decodeString = AESUtil.decrypt(tokenString, secret);
        return JSON.parseObject(decodeString, ApiToken.class);
    }

    /**
     * 令牌是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpired(ApiToken token) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date tokenTime = sdf.parse(token.getExpireTime());
            Date now = new Date();
            return now.after(tokenTime);
        } catch (Exception ex) {
            LOGGER.error("isExpired", ex);
            throw new RuntimeException(ex);
        }
    }
}