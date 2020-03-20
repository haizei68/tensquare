package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDao extends MongoRepository<Comment,String> {
}
