package com.gjp.sms.service;

import com.gjp.sms.vo.Result;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Map;

public interface MailService {
    /**
     * 简单邮件发送
     * @param sendTo 目标邮箱账号
     * @param subject 主题
     * @param msg 内容
     */
    void sendSimpleMail(String sendTo, String subject, String msg);

    /**
     * 发送带附件的邮件
     * @param sendTo 目标邮箱账号
     * @param subject 主题
     * @param text 发送内容
     * @param attachmentMap 附件
     */
    void sendHtmlMail(String sendTo,String subject, String text, Map<String, File> attachmentMap);

    /**
     * 发送模板邮件
     * @param sendTo
     * @param subject
     * @param params
     */
    void sendTemplateMail(String sendTo,String subject, Map<String, Object> params);

}
