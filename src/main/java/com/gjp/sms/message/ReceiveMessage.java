package com.gjp.sms.message;

import com.gjp.sms.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "queue-1")
public class ReceiveMessage {
    Logger log = LoggerFactory.getLogger(ReceiveMessage.class);
    @RabbitHandler
    public  void process(Object cotent){
            log.info("接收到信息--"+cotent.toString());
    }
}
