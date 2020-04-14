package com.tensquare.asset.controller.user;

import com.tensquare.asset.service.user.IUser;
import com.tensquare.asset.entity.*;
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
