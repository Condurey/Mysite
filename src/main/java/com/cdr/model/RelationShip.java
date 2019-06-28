package com.cdr.model;

import lombok.Data;

/**
 * 文章关联信息表
 */
@Data
public class RelationShip {

    /**
     * 文章主键编号
     */
    private Integer cid;
    /**
     * 项目编号
     */
    private Integer mid;
}
