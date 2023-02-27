package com.ilovesshan.couponunion.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
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
    private static ProgressDialog progressDialog;

    /**
     * 防止多个Toast重叠
     *
     * @param message 提示信息
     */
    public static void showMessage(String message) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    /**
     * 加载中loading
     *
     * @param context    显示 loading
     * @param message    文字提示信息
     * @param canDismiss ProgressDialog 是否可以按返回键取消
     */
    public static void showLoading(Context context, String message, boolean canDismiss) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(canDismiss);
        }
        progressDialog.setMessage(TextUtils.isEmpty(message) ? "正在努力加载中..." : message);
        progressDialog.show();
    }


    /**
     * 加载中loading
     *
     * @param context 显示 loading
     * @param message 文字提示信息
     */
    public static void showLoading(Context context, String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        progressDialog.setMessage(TextUtils.isEmpty(message) ? "正在努力加载中..." : message);
        progressDialog.show();
    }


    /**
     * 关闭 loading
     */
    public static void dismissLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
