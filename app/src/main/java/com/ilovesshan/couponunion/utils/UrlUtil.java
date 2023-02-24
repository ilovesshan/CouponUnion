package com.ilovesshan.couponunion.utils;

import com.ilovesshan.couponunion.config.ApiConfig;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/24
 * @description:
 */
public class UrlUtil {

    public static String getOptimizationImageUrl(String path, int dynamicSize) {
        if (dynamicSize <= 50) {
            dynamicSize = 50;
        } else if (dynamicSize <= 100) {
            dynamicSize = 100;
        } else if (dynamicSize <= 150) {
            dynamicSize = 150;
        } else if (dynamicSize <= 200) {
            dynamicSize = 200;
        } else if (dynamicSize <= 250) {
            dynamicSize = 250;
        } else if (dynamicSize <= 300) {
            dynamicSize = 300;
        } else if (dynamicSize <= 500) {
            dynamicSize = 500;
        }
        return ApiConfig.PROTOCOL + path + "_" + dynamicSize + "x" + dynamicSize;
    }

    public static String getNormalImageUrl(String path) {
        return ApiConfig.PROTOCOL + path;
    }
}
