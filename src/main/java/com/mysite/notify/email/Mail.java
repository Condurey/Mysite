package com.mysite.notify.email;

import lombok.Data;
import org.thymeleaf.context.Context;

import java.io.Serializable;

@Data
public class Mail implements Serializable {

    private static final long serialVersionUID = -977012159996387163L;

    private String sender;

    /**
     * 邮件接收人
     */
    private String recipient;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 文件附件路径
     */
    private String attachPath;

    /**
     * 附件名称
     */
    private String attachName;

    /**
     * 图片路径
     */
    private String rscPath;

    /**
     * 图片ID，用于在<img>标签中使用，从而显示图片
     */
    private String rscId;

    /**
     * 模板
     */
    private String template;

    /**
     * 模板传值
     */
    private Context context;

}
