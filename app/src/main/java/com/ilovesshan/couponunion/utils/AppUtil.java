package com.ilovesshan.couponunion.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/26
 * @description:
 */
public class AppUtil {

    /**
     * 检测手机中是否已经安装某个应用
     *
     * @param context     context
     * @param packageName 应用包名
     */
    public static boolean checkIsInstallApp(Context context, String packageName) {
        boolean installedApp = false;
        try {
            final ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            installedApp = applicationInfo != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return installedApp;
    }


    /**
     * 跳转到App应用中
     *
     * @param context context
     * @param pkg     pkg
     * @param cls     cls
     */
    public static void jumpToApp(Context context, String pkg, String cls) {
        final Intent intent = new Intent();
        final ComponentName componentName = new ComponentName(pkg, cls);
        intent.setComponent(componentName);
        context.startActivity(intent);
    }
}
