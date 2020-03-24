package com.tensquare.asset.controller;

import com.tensquare.asset.service.IUser;
import entity.ApiRequest;
import entity.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUser userService;

    @RequestMapping(path = "findAll",method = RequestMethod.GET)
    public ApiResponse findAll(){
        return userService.findAll();
    }


    @RequestMapping(path = "findById",method = RequestMethod.POST)
    public ApiResponse findById(@RequestBody ApiRequest<String> req){
        return userService.findById(req);
    }
}
