package com.mysite.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class Relationship implements Serializable {

    private static final long serialVersionUID = -739986251231085624L;
    /**
     * 内容主键
     */
    private Integer cid;

    /**
     * 项目主键
     */
    private Integer mid;
}