package com.mysite.model.dto;


import com.mysite.model.entity.Content;
import lombok.Data;

import java.util.List;

/**
 * 文章归档类
 * Created by Donghua.Chen on 2018/4/30.
 */
@Data
public class ArchiveDto {

    private String date;
    private String count;
    private List<Content> articles;
}
