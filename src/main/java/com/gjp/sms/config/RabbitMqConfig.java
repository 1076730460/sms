package com.gjp.sms.config;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 *<p>RabbitMQ配置</p>
 * @author gengjiaping
 * @date  2019-04-12
 */

@Slf4j
@Configuration
public class RabbitMqConfig {
    @Resource
    private QueueConfig queueConfig;

    /**
     * 链接工厂
     */
    @Resource
    private ConnectionFactory connectionFactory;

    @Resource
    private ExchangeConfig exchangeConfig;

    /**
     * 将消息队列和交换机进行绑定
     */
    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(queueConfig.mailQueue()).to(exchangeConfig.mailExchange()).with("mail.routing.key.name");
    }

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer( RabbitProperties config){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        RabbitProperties.Listener listenerConfig = config.getListener();
        factory.setAutoStartup(listenerConfig.getSimple().isAutoStartup());
        factory.setConcurrentConsumers(listenerConfig.getSimple().getConcurrency());
        return factory;
    }


    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean("rabbitTemplate")
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

    /**
     * 发送消息确认回调函数
     */
    class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback{

        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            if (b) {
                log.info("消息消费成功");
            } else {
                log.info("消息消费失败:{} 重新发送",s);
            }
        }
    }
}