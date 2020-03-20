package com.tensquare.recruit.controller;

import com.tensquare.recruit.pojo.Comment;
import com.tensquare.recruit.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return  new Result(true, StatusCode.OK,"提交成功");
    }

    @RequestMapping(value ="/{id}" ,method = RequestMethod.GET)
    public Result save(@PathVariable String id){
        return  new Result(true, StatusCode.OK,"查询成功",commentService.findById(id));
    }
}
