package com.ilovesshan.couponunion.view;

import com.ilovesshan.couponunion.base.BaseViewCallback;
import com.ilovesshan.couponunion.model.entity.Category;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface IHomeCategoryDetailViewCallBack extends BaseViewCallback {
    /**
     * 分类详情数据加载成功的结果
     *
     * @param categoryDetail categoryDetail
     */
    void onCategoryDetailResult(CategoryDetail categoryDetail);


    /**
     * 数据请求失败
     */
    void onLoadMoreError();

    /**
     * 数据请求结果为空
     */
    void onLoadMoreEmpty();

    /**
     * 数据请求中
     */
    void onLoadMoreLoading();

    /**
     * 获取当前分类的ID
     *
     * @return 当前分类的ID
     */
    int getCurrentCategoryId();
}
