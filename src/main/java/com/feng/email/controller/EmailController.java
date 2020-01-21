package com.feng.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Description 邮件测试
 * @Author fengwen
 * @Date 2020/1/21 17:04
 * @Version V1.0
 */
@RestController
@RequestMapping("email")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("send")
    public String sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wenfeng1477452395@163.com");
        message.setTo("1477452395@qq.com");
        message.setSubject("test");
        message.setText("this springboot email");
        mailSender.send(message);
        return "发送成功";
    }

    @GetMapping("send2")
    public String sendAttachmentMail() throws MessagingException {

        MimeMessage mimeMailMessage = null;

        mimeMailMessage = mailSender.createMimeMessage();
        //true 表示需要创建一个multipart message
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom("wenfeng1477452395@163.com");
        mimeMessageHelper.setTo("1477452395@qq.com");
        mimeMessageHelper.setSubject("复杂邮件");
        mimeMessageHelper.setText("带有图片的邮件");
        //文件路径 目前写死在代码中，之后可以当参数传过来，或者在MailBean中添加属性absolutePath
        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\华硕电脑\\Pictures\\nv1.jpg"));
        String fileName = "nv1.jpg";
        //添加附件,第一个参数表示添加到 Email 中附件的名称，第二个参数是图片资源
        mimeMessageHelper.addAttachment(fileName, file);
        
        //多个附件
        //mimeMessageHelper.addAttachment(fileName1, file1);

        mailSender.send(mimeMailMessage);
        return "发送成功";
    }

}
