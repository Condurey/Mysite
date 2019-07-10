package com.mysite.model.bo;

import com.mysite.model.po.Content;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 13 on 2017/2/23.
 */
@Data
public class ArchiveBo implements Serializable {

    private String date;
    private String count;
    private List<Content> articles;

    @Override
    public String toString() {
        return "Archive [" +
                "date='" + date + '\'' +
                ", count='" + count + '\'' +
                ", articles=" + articles +
                ']';
    }
}
