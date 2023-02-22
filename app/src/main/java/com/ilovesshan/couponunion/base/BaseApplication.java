package com.ilovesshan.couponunion.base;

import android.app.Application;

import com.ilovesshan.couponunion.utils.LogUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initialization();
    }

    private void initialization() {
        // 初始化日志工具
        LogUtil.init(getPackageName(), false);
    }
}
