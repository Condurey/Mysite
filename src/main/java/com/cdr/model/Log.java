package com.cdr.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 日志类
 */
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 6357404782062324789L;
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
