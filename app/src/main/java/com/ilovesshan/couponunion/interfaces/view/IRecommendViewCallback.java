package com.ilovesshan.couponunion.interfaces.view;

import com.ilovesshan.couponunion.base.BaseViewCallback;
import com.ilovesshan.couponunion.entity.RecommendNav;
import com.ilovesshan.couponunion.entity.RecommendNavList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */
public interface IRecommendViewCallback extends BaseViewCallback {

    /**
     * 推荐分类导航数据请求结果
     *
     * @param recommendNav recommendNav
     */
    void onRecommendNavResult(RecommendNav recommendNav);


    /**
     * 推荐分类导航列表数据请求结果
     *
     * @param recommendNavList recommendNavList
     */
    void onRecommendListResult(RecommendNavList recommendNavList);
}
