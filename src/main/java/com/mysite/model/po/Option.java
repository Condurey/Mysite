package com.mysite.model.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author
 */
@Data
public class Option implements Serializable {

    private static final long serialVersionUID = -6326462763124520977L;

    /**
     * 名称
     */
    private String name;
    /**
     * 内容
     */
    private String value;
    /**
     * 备注
     */
    private String description;
}