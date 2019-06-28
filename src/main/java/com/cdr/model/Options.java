package com.cdr.model;

import lombok.Data;

/**
 * 网站配置项
 */
@Data
public class Options {

    /** 名称 */
    private String name;
    /** 内容 */
    private String value;
    /** 备注 */
    private String description;
}
