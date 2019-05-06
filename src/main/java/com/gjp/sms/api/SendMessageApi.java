package com.gjp.sms.api;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class SendMessageApi {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     *
     * @param message
     */
    public void send(Object message) {
        try {
            rabbitTemplate.convertAndSend("mail.exchange.name", "mail.routing.key.name",
                    MessageBuilder.withBody((message.toString()).getBytes("utf-8")).build());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // rabbitTemplate.convertAndSend("first-queue",message);
    }

   @RequestMapping(value = "/sendMsgtest")
    public void sendMsgTest(){
        send("邮件测试");
    }


}
