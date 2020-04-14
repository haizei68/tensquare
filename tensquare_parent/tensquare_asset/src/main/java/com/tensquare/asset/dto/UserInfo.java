package com.tensquare.asset.dto;

import java.util.List;

public class UserInfo {

    private String id; //pk

    private String name;//用户名

    private String organizationName; //团队名

    private String token; //令牌

    private List<Role> roles;//角色集

    private List<String> permissionPoints; //权限点

    private Boolean isAdmin; //是否超级用户

    private Boolean isLogin; //登陆来源是Login 还是 eip

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public List<String> getPermissionPoints() {
        return permissionPoints;
    }

    public void setPermissionPoints(List<String> permissionPoints) {
        this.permissionPoints = permissionPoints;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
