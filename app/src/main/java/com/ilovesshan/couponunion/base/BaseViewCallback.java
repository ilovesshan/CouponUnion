package com.ilovesshan.couponunion.base;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface BaseViewCallback {
    /**
     * 数据请求失败
     */
    void onError();

    /**
     * 数据请求结果为空
     */
    void onEmpty();

    /**
     * 数据请求中
     */
    void onLoading();

    /**
     * 数据请求成功
     */
    void onSuccess();
}
