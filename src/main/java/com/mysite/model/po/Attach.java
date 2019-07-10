package com.mysite.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class Attach implements Serializable {

    private static final long serialVersionUID = -3138664141979498937L;

    /**
     * 主键编号
     */
    private Integer id;
    /**
     * 文件名称
     */
    private String fname;
    /**
     * 文件类型
     */
    private String ftype;
    /**
     * 文件的地址
     */
    private String fkey;
    /**
     * 创建人的id
     */
    private Integer authorId;
    /**
     * 创建的时间戳
     */
    private Integer created;


}