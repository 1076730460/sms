package com.gjp.sms.message;

import com.gjp.sms.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息推送
 * @author gengjiaping
 * @date  2019-4-16
 */

@Component
public class SendMessage {

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

}
