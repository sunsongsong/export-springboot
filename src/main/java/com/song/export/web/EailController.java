package com.song.export.web;

import com.song.export.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping(value = "/eail", produces = "application/json;charset=utf-8")
public class EailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    final String to = "xxoo@xxoo.com";

    @RequestMapping(value = "/test1")
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail(to,"test simple mail"," hello this is simple mail");
    }

    @RequestMapping(value = "/test2")
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail(to,"test simple mail",content);
    }

    @RequestMapping(value = "/test3")
    public void sendAttachmentsMail() {
        String filePath="D:\\222.docx";
        mailService.sendAttachmentsMail(to, "主题：带附件的邮件", "有附件，请查收！", filePath);
    }

    @RequestMapping(value = "/test4")
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "F:\\我的整理\\冯晨晨.jpg";

        mailService.sendInlineResourceMail(to, "主题：这是有图片的邮件", content, imgPath, rscId);
    }

    @RequestMapping(value = "/test5")
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

        mailService.sendHtmlMail(to,"主题：这是模板邮件",emailContent);
    }

}
