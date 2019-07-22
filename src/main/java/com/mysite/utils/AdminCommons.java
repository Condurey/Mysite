package com.mysite.utils;


import com.mysite.model.po.Meta;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 后台公共函数
 * <p>
 * Created by Luis on 2018/4/30.
 */
@Component
public final class AdminCommons {

    private static final Random random = new Random();
    
    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    /**
     * 判断category和cat的交集
     *
     * @param cats
     * @return
     */
    public static boolean exist_cat(Meta category, String cats) {
        String[] arr = StringUtils.split(cats, ",");
        if (null != arr && arr.length > 0) {
            for (String c : arr) {
                if (c.trim().equals(category.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String rand_color() {
        int r = rand(0, COLORS.length - 1);
        return COLORS[r];
    }

    public static int rand(int min, int max) {
        return random.nextInt(max) % (max - min + 1) + min;
    }

}
