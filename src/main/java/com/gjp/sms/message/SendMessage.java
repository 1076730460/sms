package com.gjp.sms.message;

import com.gjp.sms.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
     * @param message
     */
    @RabbitListener(queues="topic.message")
    public void send(Object message) {
        rabbitTemplate.convertAndSend("exchang", "topic.message",
                message);
    }

}
