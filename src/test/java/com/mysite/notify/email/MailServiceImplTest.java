package com.mysite.notify.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendSimpleMail() {
        Mail mail = new Mail();
        mail.setSender("condurey@163.com");
        mail.setRecipient("515947025@qq.com");
        mail.setSubject("发送测试邮件");
        mail.setContent("这是一封测试邮件");
        mailService.sendSimpleMail(mail);
    }


    @Test
    public void sendInlineResourceMail() {
        //序列号必须使用001
        String rscId = "001";
        String rscPath = "C:\\Users\\kuang\\Desktop\\常用git指令.jpg";
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有图片哦" + "</font></h3>"
                + "<img src=\'cid:" + rscId + "\'></body></html>";
        Mail mail = new Mail();
        mail.setSender("condurey@163.com");
        mail.setRecipient("515947025@qq.com");
        mail.setSubject("发送测试邮件");
        mail.setContent(content);
        mail.setRscId(rscId);
        mail.setRscPath(rscPath);
        mailService.sendInlineResourceMail(mail);
    }

    @Test
    public void sendAttachmentMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有附件哦" + "</font></h3></body></html>";
        String filePath = "C:\\Users\\kuang\\Desktop\\常用git指令.jpg";

        Mail mail = new Mail();
        mail.setSender("condurey@163.com");
        mail.setRecipient("515947025@qq.com");
        mail.setSubject("发送测试邮件");
        mail.setContent(content);
        mail.setAttachPath(filePath);
        mail.setAttachName("常用git指令.jpg");
        mailService.sendAttachmentMail(mail);
    }

    @Test
    public void sendHtmlMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件" + "</font></h3></body></html>";
        Mail mail = new Mail();
        mail.setSender("condurey@163.com");
        mail.setRecipient("515947025@qq.com");
        mail.setSubject("发送测试邮件");
        mail.setContent(content);

        mailService.sendHtmlMail(mail);
    }

    @Test
    public void sendTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "001");
        Mail mail = new Mail();
        mail.setSender("condurey@163.com");
        mail.setRecipient("515947025@qq.com");
        mail.setSubject("发送测试邮件");
        mail.setTemplate("/email/emailTemplate");
        mail.setContext(context);
        mailService.sendTemplateMail(mail);


    }
}