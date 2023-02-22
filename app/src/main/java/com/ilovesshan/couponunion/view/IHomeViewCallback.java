package com.ilovesshan.couponunion.view;

import com.ilovesshan.couponunion.model.entity.Categories;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface IHomeViewCallback {
    /**
     * 分类数据加载成功的结果
     *
     * @param categories categories
     */
    void onCategoriesResult(Categories categories);
}
