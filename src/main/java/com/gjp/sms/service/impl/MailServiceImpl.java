package com.gjp.sms.service.impl;

import com.gjp.sms.common.MailProperties;
import com.gjp.sms.service.MailService;
import com.gjp.sms.vo.Result;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void sendSimpleMail(String sendTo, String subject, String msg) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getFrom());
        mailMessage.setTo(sendTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(msg);
        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendHtmlMail(String sendTo, String subject, String text, Map<String, File> attachmentMap){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //是否发送的邮件是富文本（附件，图片，html等）
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(mailProperties.getFrom());
            messageHelper.setTo(sendTo);
            messageHelper.setSubject(subject);
            //重点，默认为false，显示原始html代码，无效果
            messageHelper.setText(text, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        //附件
        if(attachmentMap != null){
            MimeMessageHelper finalMessageHelper = messageHelper;
            attachmentMap.entrySet().stream().forEach(entrySet -> {
                File file = entrySet.getValue();
                if(file.exists()){
                    try {
                        finalMessageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendTemplateMail(String sendTo, String subject, Map<String, Object> params) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailProperties.getFrom());
            helper.setTo(sendTo);
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setClassForTemplateLoading(this.getClass(), "/templates");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), params);
            helper.setSubject(subject);
            //重点，默认为false，显示原始html代码，无效果
            helper.setText(html, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
