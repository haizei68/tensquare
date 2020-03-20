package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * spring data jpa
 * 标签dao层
 * JpaRepository:基本的CRUD
 *JpaSpecificationExecutor:复杂查询（分页+条件查询 。。。。）
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {

}
