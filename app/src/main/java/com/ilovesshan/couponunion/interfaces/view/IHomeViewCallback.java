package com.ilovesshan.couponunion.interfaces.view;

import com.ilovesshan.couponunion.base.BaseViewCallback;
import com.ilovesshan.couponunion.entity.Category;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface IHomeViewCallback extends BaseViewCallback {
    /**
     * 分类数据加载成功的结果
     *
     * @param category category
     */
    void onCategoryResult(Category category);
}
