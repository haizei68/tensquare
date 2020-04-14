package com.tensquare.sms;

import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信监听类
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    private  static final Logger LOGGER = LoggerFactory.getLogger(SmsListener.class);

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String temlateCode;

    @Value("${aliyun.sms.sign_name}")
    private String signName;

    /**
     *  发送短信  RabbitHandler:处理消息
     * @param message
     */
    @RabbitHandler
    public void sendSms(Map<String,String> message){
        LOGGER.info("手机号："+message.get("mobile"));
        LOGGER.info("验证码："+message.get("code"));

        try {
            smsUtil.sendSms(message.get("mobile"),temlateCode,signName,"{\"code\":\""+ message.get("code") +"\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}