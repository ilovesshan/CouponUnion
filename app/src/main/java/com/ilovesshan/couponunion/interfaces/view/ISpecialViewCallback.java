package com.ilovesshan.couponunion.interfaces.view;

import com.ilovesshan.couponunion.base.BaseViewCallback;
import com.ilovesshan.couponunion.entity.Special;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */
public interface ISpecialViewCallback extends BaseViewCallback {

    /**
     * 特惠数据列表
     */
    void onSpecialDataResult(Special special);


    /**
     * 上拉加载特惠数据列表
     */
    void onLoadMoreSpecialDataResult(Special special);


    /**
     * 加载更多 失败
     */
    void onLoadMoreError();


    /**
     * 加载更多 数据为空
     */
    void onLoadMoreEmpty();
}
