package com.ilovesshan.couponunion.presenter;

import com.ilovesshan.couponunion.base.BasePresenter;
import com.ilovesshan.couponunion.view.IHomeViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface IHomePresenter extends BasePresenter<IHomeViewCallback> {
    /**
     * 获取分类数据
     */
    void getCategory();
}
