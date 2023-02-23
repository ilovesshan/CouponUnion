package com.ilovesshan.couponunion.presenter;

import com.ilovesshan.couponunion.base.BasePresenter;
import com.ilovesshan.couponunion.view.IHomeCategoryDetailViewCallBack;
import com.ilovesshan.couponunion.view.IHomeViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface IHomeCategoryDetailPresenter extends BasePresenter<IHomeCategoryDetailViewCallBack> {

    /**
     * 获取分类详情数据
     *
     * @param categoryId 分类ID
     * @param page       页码
     */
    void getCategoryDetail(int categoryId, int page);

    /**
     * 上拉加载更多
     *
     * @param categoryId 分类ID
     * @param page       页码
     */
    void loadMore(int categoryId, int page);

    /**
     * 下拉刷新
     *
     * @param categoryId 分类ID
     * @param page       页码
     */
    void refresh(int categoryId, int page);
}
