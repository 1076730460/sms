package com.gjp.sms.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件发送配置类
 * @author gengjiaping
 * @date 2019-05-05
 */
@Component
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private String from;

    private String to;


}

