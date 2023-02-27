package com.ilovesshan.couponunion.interfaces.presenter;

import com.ilovesshan.couponunion.base.BasePresenter;
import com.ilovesshan.couponunion.interfaces.view.ISpecialViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */
public interface ISpecialPresenter extends BasePresenter<ISpecialViewCallback> {

    /**
     * 获取 特惠数据
     *
     */
    void getSpecialData();


    /**
     * 获取 上拉加载特惠数据
     *
     */
    void loadMoreSpecialData();

}
