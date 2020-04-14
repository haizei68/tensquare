package com.tensquare.asset.service.auth;

import com.tensquare.asset.dto.UserInfo;
import com.tensquare.asset.entity.ApiRequest;
import com.tensquare.asset.entity.ApiResponse;

public interface IAuth {

    /**
     * 登录获取令牌
     *
     * @param req
     * @return
     */
    ApiResponse<UserInfo> login(ApiRequest<Void> req);

    /**
     * 刷新令牌
     *
     * @param req
     * @return
     */
    ApiResponse<String> refreshToken(ApiRequest<Void> req);

    /**
     * 通过EIP的登录(不需密码)
     * @param req
     * @return
     */

    ApiResponse<UserInfo> loginFromEip(ApiRequest<Void> req);

    /**
     * 根据token获取用户信息
     * @param req
     * @return
     */
    ApiResponse<UserInfo> getUserInfoByToken(ApiRequest<Void> req);

}
