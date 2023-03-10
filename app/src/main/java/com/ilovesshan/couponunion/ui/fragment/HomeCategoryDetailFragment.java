package com.ilovesshan.couponunion.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.config.ApiConfig;
import com.ilovesshan.couponunion.config.Constants;
import com.ilovesshan.couponunion.entity.CategoryDetail;
import com.ilovesshan.couponunion.interfaces.view.IHomeCategoryDetailViewCallBack;
import com.ilovesshan.couponunion.presenter.HomeCategoryDetailPresenter;
import com.ilovesshan.couponunion.ui.activites.TicketActivity;
import com.ilovesshan.couponunion.ui.adapter.HomeCategoryDetailAdapter;
import com.ilovesshan.couponunion.ui.adapter.HomeCategorySwiperAdapter;
import com.ilovesshan.couponunion.utils.PresenterManager;
import com.ilovesshan.couponunion.utils.ScreenUtil;
import com.ilovesshan.couponunion.utils.ToastUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.customviews.YfNestedScrollView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

    @BindView(R.id.smart_refresh)
    public SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.category_home_container)
    public LinearLayout categoryHomeContainer;

    @BindView(R.id.category_list)
    public RecyclerView categoryList;

    @BindView(R.id.category_swiper)
    public ViewPager categorySwiper;

    @BindView(R.id.category_title)
    public TextView tvCategoryTitle;

    @BindView(R.id.swiper_indicator_container)
    public LinearLayout swiperIndicatorSwiper;

    @BindView(R.id.category_home_nested_scroll_view)
    public YfNestedScrollView categoryHomeNestedScrollView;

    @BindView(R.id.swiper_and_title_container)
    public LinearLayout swiperAndTitleContainer;


    private HomeCategorySwiperAdapter homeCategorySwiperAdapter;
    private int swiperCountLength = 0;
    private TimerTask timerTask;
    private Timer timer;

    /**
     * ????????????HomeCategoryFragment ?????????????????????
     *
     * @param categoryId    ??????ID
     * @param categoryTitle ????????????
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
        // ??????????????? ???????????????????????????????????????
        categoryList.setLayoutManager(new LinearLayoutManager(getContext()));
        homeCategoryDetailAdapter = new HomeCategoryDetailAdapter();
        categoryList.setAdapter(homeCategoryDetailAdapter);

        // ?????????????????????????????????
        homeCategorySwiperAdapter = new HomeCategorySwiperAdapter();
        categorySwiper.setAdapter(homeCategorySwiperAdapter);

        // ???????????? item????????????
        homeCategoryDetailAdapter.setOnItemClick(data -> {
            HandleJumpToTickPage(data);
        });

        // ????????? item????????????
        homeCategorySwiperAdapter.setOnItemClick(data -> {
            HandleJumpToTickPage(data);
        });

        // ?????????????????????
        categorySwiper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (swiperCountLength != 0) {
                    int realPosition = position % swiperCountLength;
                    changeSwiperIndicator(realPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // ????????????RecyclerView???????????????NestedScrollView??????RecyclerView???item?????????????????????
        categoryHomeContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (categoryHomeContainer != null) {
                    final int measuredHeight = categoryHomeContainer.getMeasuredHeight();
                    final ViewGroup.LayoutParams layoutParams = categoryList.getLayoutParams();

                    // ??????(????????? + ??????????????????)
                    final int swiperAndTitleContainerMeasuredHeight = swiperAndTitleContainer.getMeasuredHeight();
                    if (swiperAndTitleContainerMeasuredHeight > 0) {
                        categoryHomeNestedScrollView.setNeedConsumeHeight(swiperAndTitleContainerMeasuredHeight);
                    }
                    layoutParams.height = measuredHeight;
                    categoryList.setLayoutParams(layoutParams);
                    if (measuredHeight > 0) {
                        categoryHomeContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });

        // ???????????????????????????????????????????????????
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableRefresh(true);

        // ????????????
        smartRefreshLayout.setOnRefreshListener(refresh -> {
            smartRefreshLayout.finishRefresh(2000);
        });

        // ????????????
        smartRefreshLayout.setOnLoadMoreListener(refresh -> {
            homeCategoryDetailPresenter.loadMore(categoryId);
        });

    }

    /**
     * ????????????????????????????????????
     *
     * @param data
     */
    private void HandleJumpToTickPage(CategoryDetail.Data data) {
        final String targetUrl = TextUtils.isEmpty(data.getCoupon_click_url()) ? data.getClick_url() : data.getCoupon_click_url();
        final Intent intent = new Intent(getContext(), TicketActivity.class);
        intent.putExtra("title", data.getTitle());
        intent.putExtra("clickUrl", ApiConfig.PROTOCOL + targetUrl);
        intent.putExtra("cover", ApiConfig.PROTOCOL + data.getPict_url());
        startActivity(intent);
    }


    /**
     * ???????????????????????????
     *
     * @param realPosition ?????????????????????
     */
    private void changeSwiperIndicator(int realPosition) {
        if (swiperIndicatorSwiper != null) {
            for (int i = 0; i < swiperIndicatorSwiper.getChildCount(); i++) {
                final View swiperChild = swiperIndicatorSwiper.getChildAt(i);
                if (i == realPosition) {
                    swiperChild.setBackgroundResource(R.drawable.shape_swiper_indicator_selected);
                } else {
                    swiperChild.setBackgroundResource(R.drawable.shape_swiper_indicator_un_selected);
                }
            }
        }
    }

    @Override
    protected void initPresenter() {
        homeCategoryDetailPresenter = PresenterManager.getInstance().getHomeCategoryDetailPresenter();
        homeCategoryDetailPresenter.registerViewCallBack(this);
    }

    @Override
    protected void loadData() {
        final Bundle bundle = getArguments();
        categoryId = bundle.getInt(Constants.HOME_CATEGORY_PAGER_ID);
        categoryTitle = bundle.getString(Constants.HOME_CATEGORY_PAGER_TITLE);

        // ??????????????????
        homeCategoryDetailPresenter.getCategoryDetail(categoryId, 1);

        // ??????????????????
        tvCategoryTitle.setText(categoryTitle);
    }

    @Override
    public void onCategoryDetailResult(CategoryDetail categoryDetail) {
        homeCategoryDetailAdapter.setCategoryDetailList(categoryDetail.getData());
    }

    @Override
    public void onCategoryDetailLoadMoreResult(CategoryDetail categoryDetail) {
        ToastUtil.showMessage("???????????????" + categoryDetail.getData().size() + "?????????!");
        homeCategoryDetailAdapter.addData(categoryDetail.getData());
        smartRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void onCategorySwiperResult(List<CategoryDetail.Data> smallImages) {
        this.swiperCountLength = smallImages.size();
        homeCategorySwiperAdapter.setData(smallImages);
        // ??????targetNum???smallImages.size()?????????
        int targetNum = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % smallImages.size());
        // ????????????????????????
        if (categorySwiper != null) {
            categorySwiper.setCurrentItem(targetNum);
        }
        // ????????????????????????
        final int px10 = ScreenUtil.dip2px(getContext(), 10);
        final int px5 = ScreenUtil.dip2px(getContext(), 5);
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(px10, px10);
        layoutParams.setMargins(px5, 0, px5, 0);
        for (int i = 0; i < smallImages.size(); i++) {
            final View indicatorItemView = new View(getContext());
            indicatorItemView.setLayoutParams(layoutParams);
            if (i == 0) {
                indicatorItemView.setBackgroundResource(R.drawable.shape_swiper_indicator_selected);
            } else {
                indicatorItemView.setBackgroundResource(R.drawable.shape_swiper_indicator_un_selected);
            }
            if (swiperIndicatorSwiper != null) {
                swiperIndicatorSwiper.addView(indicatorItemView);
            }
        }
    }

    @Override
    public void onLoadMoreError() {
        ToastUtil.showMessage("???????????????????????????????????????");
        smartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.showMessage("????????????????????????");
        smartRefreshLayout.finishLoadMore(0, true, false);
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
        homeCategoryDetailPresenter.getCategoryDetail(categoryId, 1);
    }
}
