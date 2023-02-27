package com.ilovesshan.couponunion.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.ilovesshan.couponunion.utils.LogUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class BaseApplication extends Application {

    private static Context context;
    private static Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();

        initialization();
    }

    private void initialization() {
        context = getBaseContext();
        // 初始化日志工具
        LogUtil.init(getPackageName(), false);

        // 初始化mHandler
        handler = new Handler();
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }
}
