package com.mysite.model.dto;

import lombok.Data;

/**
 * Created by Donghua.Chen on 2018/4/29.
 */
@Data
public class AttachDto extends BaseDto {

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

    @Override
    public String toString() {
        return "AttachDto{" +
                "id=" + id +
                ", userName=" + this.getUserName() +
                ", fname=" + fname +
                ", ftype=" + ftype +
                ", authorId=" + authorId +
                ", created=" + created +
                '}';
    }

}
