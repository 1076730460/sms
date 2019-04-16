package com.gjp.sms.mqcallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 消息发送到交换机确认机制
 * @author gengjiaping
 * @date 2019-4-16
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    private Logger log = LoggerFactory.getLogger(MsgSendConfirmCallBack.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        log.info("MsgSendConfirmCallBack  , 回调id:{}" ,correlationData);
        if (b) {
            log.info("消息消费成功");
        } else {
            log.info("消息消费失败:{}重新发送",s);
        }

    }
}
