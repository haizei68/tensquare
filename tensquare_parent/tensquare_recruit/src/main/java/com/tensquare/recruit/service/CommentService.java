package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.CommentDao;
import com.tensquare.recruit.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    /**
      * @auther ZPF
      * @description: 新增
      * @date 2020/3/20
      * @return  void
      */
    public  void  add(Comment comment){
        comment.set_id(String.valueOf(idWorker.nextId()));
        comment.setPublishdate(new Date());
        commentDao.save(comment);
    }


    /**
      * @auther ZPF
      * @description: 根据id查询
      * @date 2020/3/20
      * @return  com.tensquare.recruit.pojo.Comment
      */
    public  Comment findById(String id){
        return commentDao.findById(id).get();
    }
}
