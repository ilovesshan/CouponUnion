package com.ilovesshan.couponunion.interfaces.presenter;

import com.ilovesshan.couponunion.base.BasePresenter;
import com.ilovesshan.couponunion.interfaces.view.IRecommendViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */
public interface IRecommendPresenter extends BasePresenter<IRecommendViewCallback> {

    /**
     * 获取推荐分类导航数据
     */
    void getRecommendNav();


    /**
     * 获取推荐分类导航数据对应列表
     *
     * @param categoryId 分类ID
     */
    void getRecommendList(int categoryId);
}
