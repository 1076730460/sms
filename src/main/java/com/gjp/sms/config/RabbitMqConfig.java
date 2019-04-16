package com.gjp.sms.config;


import com.gjp.sms.mqcallback.MsgSendConfirmCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;


/**
 *<p>RabbitMQ配置</p>
 * @author gengjiaping
 * @date  2019-04-12
 */


public class RabbitMqConfig {
    private Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);

    /** 消息交换机的名字*/
    public static final String EXCHANGE = "exchangeTest";

    @Resource
    private QueueConfig queueConfig;

    /**
     * 链接工厂
     */
    @Resource
    private ConnectionFactory connectionFactory;

    private ExchangeConfig exchangeConfig;

    /** 队列key1*/
    public static final String ROUTINGKEY1 = "queue_one_key1";

    public Binding binding(){
        return BindingBuilder.bind(queueConfig.queue()).to(exchangeConfig.directExchange()).with(RabbitMqConfig.ROUTINGKEY1);
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueues(queueConfig.queue());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        //设置确认模式手工确认
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return  simpleMessageListenerContainer;
    }

    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback(msgSendConfirmCallBack());
        return  template;
    }

    /**
     * 消息确认机制，如果发送失败进行重发
     * @return
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }
}