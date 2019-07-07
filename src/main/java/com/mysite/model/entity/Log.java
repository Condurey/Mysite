package com.mysite.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = -5183624834625736819L;
    /**
     * 日志主键
     */
    private Integer id;

    /**
     * 产生的动作
     */
    private String action;

    /**
     * 产生的数据
     */
    private String data;

    /**
     * 发生人id
     */
    private Integer authorId;

    /**
     * 日志产生的ip
     */
    private String ip;

    /**
     * 日志创建时间
     */
    private Integer created;

}