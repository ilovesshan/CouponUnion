package com.ilovesshan.couponunion.utils;

import android.widget.Toast;

import com.ilovesshan.couponunion.base.BaseApplication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/23
 * @description:
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 防止多个Toast重叠
     *
     * @param message 提示信息
     */
    public static void show(String message) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
