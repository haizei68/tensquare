package com.tensquare.asset.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Param: 角色信息表
 * @return:
 * @Author: hsy
 * @Date: 2019/2/27 15:45
 */
public class Role implements Serializable {

    private static final long serialVersionUID = -5262600061364377784L;

    private String id;

    private String name;

    private String isDelete;//全域数据治理专员删除标识：1可删除，0；不可删除；2020/01/07改动 dsf

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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}