package com.tensquare.spit.service;

import com.tensquare.spit.config.SpitConfig;
import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SpitConfig spitConfig;

    /**
      * @auther ZPF
      * @description: 查询全部记录
      * @date 2020/3/19
      * @return  java.util.List<Spit>
      */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
      * @auther ZPF
      * @description: 根据主键查询
      * @date 2020/3/19
      * @return  Spit
      */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
      * @auther ZPF
      * @description: 增加
      * @date 2020/3/19
      * @return  void
      */
    public void add(Spit spit){
        spit.set_id(String.valueOf(idWorker.nextId()));
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        if (spit.getParentid()!=null && StringUtils.isNotEmpty(spit.getParentid())){
            //如果存在上级ID,评论
            //父id所在评论数要+1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
      * @auther ZPF
      * @description: 修改
      * @date 2020/3/19
      * @return  void
      */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
      * @auther ZPF
      * @description: 删除
      * @date 2020/3/19
      * @return  void
      */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentid(String parentid, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid,pageRequest);
    }

    @Autowired
    private MongoTemplate mongoTemplate;
   /**
     * @auther ZPF
     * @description: 点赞
     * @date 2020/3/19
     * @return  void
     */
    public void updateThumbup(String id){
//        Spit spit = spitDao.findById(id).get();
//        spit.setThumbup(spit.getThumbup()+1);
//        spitDao.save(spit);
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,spitConfig.getSpit());
    }
}
