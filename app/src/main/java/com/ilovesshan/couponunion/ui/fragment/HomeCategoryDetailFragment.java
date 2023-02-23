package com.ilovesshan.couponunion.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.config.Constants;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;
import com.ilovesshan.couponunion.presenter.impl.HomeCategoryDetailPresenter;
import com.ilovesshan.couponunion.ui.adapter.HomeCategoryDetailAdapter;
import com.ilovesshan.couponunion.ui.adapter.HomeCategorySwiperAdapter;
import com.ilovesshan.couponunion.utils.LogUtil;
import com.ilovesshan.couponunion.view.IHomeCategoryDetailViewCallBack;

import java.util.List;

import butterknife.BindView;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
@SuppressLint("NonConstantResourceId")
public class HomeCategoryDetailFragment extends BaseFragment implements IHomeCategoryDetailViewCallBack {

    private HomeCategoryDetailPresenter homeCategoryDetailPresenter;
    private int categoryId;
    private String categoryTitle;

    private HomeCategoryDetailAdapter homeCategoryDetailAdapter;

    @BindView(R.id.category_list)
    public RecyclerView categoryList;

    @BindView(R.id.category_swiper)
    public ViewPager categorySwiper;
    private HomeCategorySwiperAdapter homeCategorySwiperAdapter;

    /**
     * 通过获取HomeCategoryFragment 实例来传递参数
     *
     * @param categoryId    分类ID
     * @param categoryTitle 分类标题
     * @return HomeCategoryFragment
     */
    public static HomeCategoryDetailFragment getHomeCategoryFragment(int categoryId, String categoryTitle) {
        HomeCategoryDetailFragment homeCategoryDetailFragment = new HomeCategoryDetailFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.HOME_CATEGORY_PAGER_ID, categoryId);
        bundle.putString(Constants.HOME_CATEGORY_PAGER_TITLE, categoryTitle);
        homeCategoryDetailFragment.setArguments(bundle);
        return homeCategoryDetailFragment;
    }

    @Override
    public int getResourceId() {
        return R.layout.fragment_category_home;
    }

    @Override
    protected void initViewAndBindEvent() {
        // 为分类列表 设置布局管理器和设置设配器
        categoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeCategoryDetailAdapter = new HomeCategoryDetailAdapter();
        categoryList.setAdapter(homeCategoryDetailAdapter);

        // 为分类轮播图设置设配器
        homeCategorySwiperAdapter = new HomeCategorySwiperAdapter();
        categorySwiper.setAdapter(homeCategorySwiperAdapter);
    }

    @Override
    protected void initPresenter() {
        homeCategoryDetailPresenter = HomeCategoryDetailPresenter.getInstance();
        homeCategoryDetailPresenter.registerViewCallBack(this);
    }

    @Override
    protected void loadData() {
        final Bundle bundle = getArguments();
        categoryId = bundle.getInt(Constants.HOME_CATEGORY_PAGER_ID);
        categoryTitle = bundle.getString(Constants.HOME_CATEGORY_PAGER_TITLE);

        LogUtil.d(HomeCategoryDetailFragment.class, "categoryId = " + categoryId);
        LogUtil.d(HomeCategoryDetailFragment.class, "categoryTitle = " + categoryTitle);

        homeCategoryDetailPresenter.getCategoryDetail(categoryId, 1);
    }

    @Override
    public void onCategoryDetailResult(CategoryDetail categoryDetail) {
        LogUtil.d(HomeCategoryDetailFragment.class, "categoryDetail = " + categoryDetail);
        homeCategoryDetailAdapter.setData(categoryDetail.getData());
    }

    @Override
    public void onCategorySwiperResult(List<CategoryDetail.Data> smallImages) {
        LogUtil.d(HomeCategoryDetailFragment.class, "smallImages = " + smallImages);
        homeCategorySwiperAdapter.setData(smallImages);
    }

    @Override
    public void onLoadMoreError() {

    }

    @Override
    public void onLoadMoreEmpty() {

    }

    @Override
    public void onLoadMoreLoading() {

    }

    @Override
    public int getCurrentCategoryId() {
        return categoryId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homeCategoryDetailPresenter != null) {
            homeCategoryDetailPresenter.unRegisterViewCallBack(this);
        }
    }

    @Override
    protected void onRetry(View v) {
        //TODO: 需要考虑页码的问题
        homeCategoryDetailPresenter.getCategoryDetail(categoryId, 1);
    }
}
