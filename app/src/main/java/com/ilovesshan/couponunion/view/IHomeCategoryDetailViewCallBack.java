package com.ilovesshan.couponunion.view;

import com.ilovesshan.couponunion.base.BaseViewCallback;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;

import java.util.List;

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
     * 分类详情数据加载更多成功的结果
     *
     * @param categoryDetail categoryDetail
     */
    void onCategoryDetailLoadMoreResult(CategoryDetail categoryDetail);

    /**
     * 分类详情数据轮播图加载成功的结果
     *
     * @param smallImages smallImages
     */
    void onCategorySwiperResult(List<CategoryDetail.Data> smallImages);

    /**
     * 数据请求失败
     */
    void onLoadMoreError();

    /**
     * 数据请求结果为空
     */
    void onLoadMoreEmpty();

    /**
     * 获取当前分类的ID
     *
     * @return 当前分类的ID
     */
    int getCurrentCategoryId();
}
