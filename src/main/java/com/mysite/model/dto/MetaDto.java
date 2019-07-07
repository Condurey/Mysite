package com.mysite.model.dto;

import com.mysite.model.entity.Meta;
import lombok.Data;

/**
 * 标签、分类列表
 * Created by Donghua.Chen on 2018/4/30.
 */
@Data
public class MetaDto extends Meta {

    private int count;
}
