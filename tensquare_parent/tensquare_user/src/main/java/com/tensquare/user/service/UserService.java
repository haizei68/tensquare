package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    private  static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserDao userDao;

    @Resource
    private IdWorker idWorker;

    /**
      * @auther ZPF
      * @description: 发送短信验证
      * @date 2020/4/13
      * @return  void
      */
    public void sendSms(String mobile){
        //1.生成六位短信验证码
        Random random = new Random();
        int max =999999;//最大数
        int min =100000;//最小数
        int code = random.nextInt(max); //随机生成
        if (code<min){
            code = code+min;
        }
        LOGGER.info(mobile+"收到验证码是:"+code);
        //2.将验证码放入到redis
       //30秒过期时间
        redisTemplate.opsForValue().set("smscode_"+mobile, code+"" ,300, TimeUnit.SECONDS );

        //3.将验证码和手机号发送到rabbitMQ中
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("code",code+"");
        rabbitTemplate.convertAndSend("sms",map);
    }

    public void add(User user,String code){
        //判断验证码是否正确
        //获取系统正确的验证码
        String syscode=(String) redisTemplate.opsForValue().get("smscode_"+user.getMobile());
        if (syscode ==null){
            throw new RuntimeException("请点击重新获取短信验证码");
        }
        if (!syscode.equals(code)){
            throw new RuntimeException("验证码输入不正确");
        }

        user.setId(String.valueOf(idWorker.nextId()));
        user.setFanscount(0);  //粉丝数
        user.setFollowcount(11);  //关注数
        user.setRegdate(new Date()); //注册日期
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());

        userDao.save(user);
    }
}
