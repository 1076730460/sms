package com.gjp.sms.service.impl;

import com.gjp.sms.common.MailProperties;
import com.gjp.sms.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void sendSimpleMail(String sendTo,String subject, String msg) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getFrom());
        mailMessage.setTo(sendTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(msg);
        javaMailSender.send(mailMessage);
    }
}
