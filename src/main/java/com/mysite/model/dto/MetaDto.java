package com.mysite.model.dto;

import com.mysite.model.po.Meta;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标签、分类列表
 * Created by Donghua.Chen on 2018/4/30.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MetaDto extends Meta {

    private int count;
}
