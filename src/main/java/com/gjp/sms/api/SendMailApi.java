package com.gjp.sms.api;

import com.gjp.sms.service.MailService;
import com.gjp.sms.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

/**
 *邮件发送
 * @author
 * @date
 */
@RestController
@RequestMapping("/api/sendmail")
@Slf4j
public class SendMailApi {

   @Resource
   private MailService  mailService;

    /**
     * 发送文字信息
     * @param sendTo 发送目标邮箱
     * @param subject 主题
     * @param msg 内容
     * @return
     */
    @GetMapping("/sendSimpleMail")
   public Result sendSimpleMail(@RequestParam("sendTo") final String sendTo,@RequestParam("subject") final String subject,@RequestParam("msg") final String msg){
       try {
           mailService.sendSimpleMail(sendTo,subject,msg);
       }catch (Exception e){
           log.error("发送邮件失败");
           return Result.error("发送邮件失败");
       }
       return Result.success("发送邮件成功");
   }

    /**
     * 发送邮件信息</br>
     * 支持发送Html和附件
     * @param sendTo 发送目标邮箱
     * @param subject  主题
     * @param msg 内容
     * @param attachmentMap 附件(k,v)k:文件名称及附件，v:附件
     * @return
     */
    @GetMapping("/sendHtmlMail")
   public Result sendHtmlMail(@RequestParam("sendTo") final String sendTo,@RequestParam("subject") final  String subject,@RequestParam("msg") final String msg,@RequestParam("attachmentMap") final Map<String, File> attachmentMap){
       try {
           mailService.sendHtmlMail(sendTo,subject,msg,attachmentMap);
       }catch (Exception e){
           log.error("发送邮件失败");
           return Result.error("发送邮件失败");
       }
       return Result.success("发送邮件成功");
   }

}
