package com.ilovesshan.couponunion.ui.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.model.entity.Category;
import com.ilovesshan.couponunion.presenter.IHomePresenter;
import com.ilovesshan.couponunion.presenter.impl.HomePresenter;
import com.ilovesshan.couponunion.ui.adapter.HomeCategoryAdapter;
import com.ilovesshan.couponunion.view.IHomeViewCallback;

import butterknife.BindView;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
@SuppressLint("NonConstantResourceId")
public class HomeFragment extends BaseFragment implements IHomeViewCallback {

    @BindView(R.id.home_tab_bar)
    public TabLayout homeTabBar;

    @BindView(R.id.home_view_pager)
    public ViewPager homeViewPager;

    private IHomePresenter presenter;
    private HomeCategoryAdapter homeCategoryAdapter;


    @Override
    public int getResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewAndBindEvent() {
        // 设置pager
        homeTabBar.setupWithViewPager(homeViewPager);
        // 设置适配器
        homeCategoryAdapter = new HomeCategoryAdapter(getChildFragmentManager());
        homeViewPager.setAdapter(homeCategoryAdapter);
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
        presenter.registerViewCallBack(this);
    }

    @Override
    protected void loadData() {
        presenter.getCategory();
    }

    @Override
    public void onCategoryResult(Category category) {
        // 分类数据获取成功
        homeCategoryAdapter.setData(category.getData());
    }

    @Override
    protected View loadBaseView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home_custom, container, false);
    }

    /**
     * 加载失败时， 重新加载
     *
     * @param v view对象
     */
    @Override
    protected void onRetry(View v) {
        presenter.getCategory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.unRegisterViewCallBack(this);
        }
    }
}
