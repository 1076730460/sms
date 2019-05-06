package com.gjp.sms.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjp.sms.config.RabbitMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ReceiveMessage {
  @Autowired
  private ObjectMapper objectMapper;

    @RabbitListener(queues="mail.queue.name",containerFactory = "multiListenerContainer")
    public  void process(Message message, Channel channel){
        System.out.println("接收到信息1--"+message.toString());
        try {
            log.debug("接收到信息1--"+message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues="topic.messages")
    public  void process2(Object cotent){
        log.info("接收到信息2--"+cotent.toString());
    }
}
