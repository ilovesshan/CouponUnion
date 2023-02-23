package com.ilovesshan.couponunion.base;

import com.ilovesshan.couponunion.view.IHomeViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface BasePresenter<T> {
    /**
     * 注册UI 回调接口
     *
     * @param callback callback
     */
    void registerViewCallBack(T callback);

    /**
     * 取消注册UI 回调接口
     *
     * @param callback callback
     */
    void unRegisterViewCallBack(T callback);
}
