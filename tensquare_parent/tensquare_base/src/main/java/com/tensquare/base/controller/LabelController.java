package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {
    private  static final Logger LOGGER = LoggerFactory.getLogger(LabelController.class);

    @Autowired
    private LabelService labelService;

    /**
     * 新增
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        LOGGER.info("新增一条数据");
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(value = "/findAll2",method = RequestMethod.GET)
    public Result findAll2(){
        List<Label> labelList = labelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",labelList);
    }
    /**
     * 查询所有
     * @return
     */
//    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
//    public ApiResponse<Result> findAll(@RequestBody ApiRequest<Void> req){
//        return labelService.findAll2(req);
//    }

    /**
     * 根据id查询详情页
     * @return
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        Label label = labelService.findById(labelId);
        return new Result(true, StatusCode.OK,"查询成功",label);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据id修改
     * @return
     */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result updateById(@PathVariable String labelId,@RequestBody Label label){
        labelService.updateById(labelId,label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /**
     * /label/search 带条件的标签数据查询
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findBySearch(@RequestBody Map searchMap){
        List<Label> labelList = labelService.findBySearch(searchMap);
        return new Result(true, StatusCode.OK,"查询单个标签数据成功",labelList);
    }

    /**
     * /label/search 带分页条件的标签数据查询
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findBySearch(@PathVariable int page,@PathVariable int size,@RequestBody Map searchMap){
        Page<Label> labelList = labelService.findByPageSearch(page,size,searchMap);
        return new Result(true, StatusCode.OK,"查询单个标签数据成功",new PageResult(labelList.getTotalElements(),labelList.getContent()));
    }
}
