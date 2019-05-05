package com.gjp.sms;

import com.gjp.sms.message.SendMessage;
import com.gjp.sms.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsApplicationTests {

    @Resource
    private SendMessage sendMessage;

   @Autowired
    private MailService mailService;

    @Test
    public void contextLoads() {
    }

    /**
     * mq消息测试
     */
    @Test
    public void sendMsgTest(){
        String uuid = UUID.randomUUID().toString();
        StringBuilder sb  = new StringBuilder("hell----");
        sendMessage.send(uuid,sb);
    }


    /**
     * 简单邮件发送测试
     */
    @Test
    public void sendSimpleMailTest(){

        mailService.sendSimpleMail("599437680@qq.com","测试Springboot发送邮件", "发送邮件测试内容.....");
    }

}
