package com.tensquare.user.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-article")
public interface ArticleClient {

    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/article/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
