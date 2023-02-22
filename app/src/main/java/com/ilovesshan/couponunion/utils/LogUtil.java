package com.ilovesshan.couponunion.utils;

import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class LogUtil {
    public static String mBaseTag = "LOG_UTIL";
    public static boolean mIsRelease = false;

    /**
     * 初始化工具
     *
     * @param baseTag   App包名
     * @param isRelease 模式
     */
    public static void init(String baseTag, boolean isRelease) {
        mBaseTag = baseTag;
        mIsRelease = isRelease;
    }

    /**
     * debug mode
     *
     * @param clazz   类字节码
     * @param content 输出信息
     */
    public static void d(Class clazz, String content) {
        if (!mIsRelease) {
            Log.d(clazz.getName(), content);
        }
    }

    /**
     * verbose mode
     *
     * @param clazz   类字节码
     * @param content 输出信息
     */
    public static void v(Class clazz, String content) {
        if (!mIsRelease) {
            Log.v(clazz.getName(), content);
        }
    }

    /**
     * info mode
     *
     * @param clazz   类字节码
     * @param content 输出信息
     */
    public static void i(Class clazz, String content) {
        if (!mIsRelease) {
            Log.i(clazz.getName(), content);
        }
    }

    /**
     * warn mode
     *
     * @param clazz   类字节码
     * @param content 输出信息
     */
    public static void w(Class clazz, String content) {
        if (!mIsRelease) {
            Log.i(clazz.getName(), content);
        }
    }

    /**
     * error mode
     *
     * @param clazz   类字节码
     * @param content 输出信息
     */
    public static void e(Class clazz, String content) {
        if (!mIsRelease) {
            Log.e(clazz.getName(), content);
        }
    }
}
