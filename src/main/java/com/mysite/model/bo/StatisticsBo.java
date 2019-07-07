package com.mysite.model.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 后台统计对象
 */
@Data
public class StatisticsBo implements Serializable {

    private Long articles;
    private Long comments;
    private Long links;
    private Long attachs;

    @Override
    public String toString() {
        return "StatisticsBo{" +
                "articles=" + articles +
                ", comments=" + comments +
                ", links=" + links +
                ", attachs=" + attachs +
                '}';
    }
}
