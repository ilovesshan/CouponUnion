package com.ilovesshan.couponunion.presenter;

import com.ilovesshan.couponunion.view.IHomeViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface IHomePresenter {
    /**
     * 获取分类数据
     */
    void getCategory();

    /**
     * 注册UI 回调接口
     *
     * @param iHomeViewCallback iHomeViewCallback
     */
    void registerViewCallBack(IHomeViewCallback iHomeViewCallback);

    /**
     * 取消注册UI 回调接口
     *
     * @param iHomeViewCallback iHomeViewCallback
     */
    void unRegisterViewCallBack(IHomeViewCallback iHomeViewCallback);
}
