package entity;

import java.io.Serializable;
import java.util.UUID;

public class ApiRequest<T> implements Serializable {
    private static final long serialVersionUID = 6088650925617493459L;
    private T bizParam;
    private String requestNo= UUID.randomUUID().toString();
    private String apiVersion="1.0.0";
    private String sysId;
    private String sign;
    private String encryptType;
    private String userId;
    private String password;
    private String token;
    private String ip;

    public ApiRequest() {
    }

    public T getBizParam() {
        return bizParam;
    }

    public void setBizParam(T bizParam) {
        this.bizParam = bizParam;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
