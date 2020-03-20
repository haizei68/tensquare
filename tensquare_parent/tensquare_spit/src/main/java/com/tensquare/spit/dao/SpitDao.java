package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 吐槽数据访问层
 * @author Administrator
 *
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    /**
      * @auther ZPF
      * @description: 根据上级ID查询吐槽列表(分页)
      * @date 2020/3/19
      * @return  org.springframework.data.domain.Page<Spit>
      */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
