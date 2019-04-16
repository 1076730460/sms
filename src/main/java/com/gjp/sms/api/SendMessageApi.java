package com.gjp.sms.api;

import com.gjp.sms.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class SendMessageApi {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     *
     * @param uuid
     * @param message
     */
    public void send(String uuid,Object message) {
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY1,
                message, correlationId);
    }

   @RequestMapping(value = "/sendMsgtest")
    public void sendMsgTest(){
        String uuid = UUID.randomUUID().toString();
        send(uuid,"123");
    }


}
