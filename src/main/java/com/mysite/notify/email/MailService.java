package com.mysite.notify.email;

public interface MailService {

    /**
     * 发送普通文本邮件
     *
     * @param mail 邮件属性
     */
    void sendSimpleMail(Mail mail);

    /**
     * 发送HTML邮件
     *
     * @param mail 邮件属性
     */
    void sendHtmlMail(Mail mail);

    /**
     * 发送带附件的邮件
     *
     * @param mail 邮件属性
     */
    void sendAttachmentMail(Mail mail);

    /**
     * 发送带图片的邮件
     *
     * @param mail 邮件属性
     */
    void sendInlineResourceMail(Mail mail);

    /**
     * 发送模板邮件
     *
     * @param mail
     */
    void sendTemplateMail(Mail mail);


}
