package com.gjp.sms.service;

public interface MailService {
    /**
     * 简单邮件发送
     * @param sendTo 目标邮箱账号
     * @param subject 主题
     * @param msg 内容
     */
    void sendSimpleMail(String sendTo,String subject, String msg);

}
