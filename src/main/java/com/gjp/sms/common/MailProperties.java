package com.gjp.sms.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件发送配置类
 * @author gengjiaping
 * @date 2019-05-05
 */
@Data
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private String from;

    private String to;


}

