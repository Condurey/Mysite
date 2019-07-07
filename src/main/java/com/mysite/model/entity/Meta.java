package com.mysite.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class Meta implements Serializable {

    private static final long serialVersionUID = -3059251338047836624L;
    /**
     * 项目主键
     */
    private Integer mid;

    /**
     * 名称
     */
    private String name;

    /**
     * 项目缩略名
     */
    private String slug;

    /**
     * 项目类型
     */
    private String type;

    /**
     * 选项描述
     */
    private String description;

    /**
     * 项目排序
     */
    private Integer sort;

    private Integer parent;

}