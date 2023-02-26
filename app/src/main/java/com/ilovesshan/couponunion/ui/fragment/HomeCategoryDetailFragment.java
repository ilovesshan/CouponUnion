package com.ilovesshan.couponunion.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.ilovesshan.couponunion.config.Constants;
import com.ilovesshan.couponunion.entity.CategoryDetail;
import com.ilovesshan.couponunion.presenter.HomeCategoryDetailPresenter;
import com.ilovesshan.couponunion.ui.adapter.HomeCategoryDetailAdapter;
import com.ilovesshan.couponunion.ui.adapter.HomeCategorySwiperAdapter;
import com.ilovesshan.couponunion.utils.ScreenUtil;
import com.ilovesshan.couponunion.utils.ToastUtil;
import com.ilovesshan.couponunion.interfaces.view.IHomeCategoryDetailViewCallBack;
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

        // 轮播图切换监听
        categorySwiper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int realPosition = position % swiperCountLength;
                // LogUtil.d(HomeCategoryDetailFragment.class, "position = " + position + ", realPosition = " + realPosition);
                changeSwiperIndicator(realPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 动态设置RecyclerView高度，解决NestedScrollView导致RecyclerView的item没达到复用效果
        categoryHomeContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final int measuredHeight = categoryHomeContainer.getMeasuredHeight();
                final ViewGroup.LayoutParams layoutParams = categoryList.getLayoutParams();

                // 获取(轮播图 + 标题栏的高度)
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
        });

        // 处理分类列表下拉刷新和上拉加载更多
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setEnableLoadMore(true);
        smartRefreshLayout.setEnableRefresh(true);

        // 下拉刷新
        smartRefreshLayout.setOnRefreshListener(refresh -> {
            smartRefreshLayout.finishRefresh(2000);
        });

        // 上拉加载
        smartRefreshLayout.setOnLoadMoreListener(refresh -> {
            homeCategoryDetailPresenter.loadMore(categoryId);
        });

        // TODO:  开启定时器任务 进行轮播图自动轮播

    }

    /**
     * 开启定时器任务 进行轮播图自动轮播
     */
    private void startLopperTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                // LogUtil.d(HomeCategoryDetailFragment.class, "定时器任务 == " + categoryId);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 2000, 2000);
    }

    /**
     * 关闭定时器任务
     */
    private void stopLopperTask() {
        if (timerTask != null) timerTask.cancel();
        if (timer != null) timer.cancel();
    }


    /**
     * 改变轮播图指示器点
     *
     * @param realPosition 当前选中点索引
     */
    private void changeSwiperIndicator(int realPosition) {
        for (int i = 0; i < swiperIndicatorSwiper.getChildCount(); i++) {
            final View swiperChild = swiperIndicatorSwiper.getChildAt(i);
            if (i == realPosition) {
                swiperChild.setBackgroundResource(R.drawable.shape_swiper_indicator_selected);
            } else {
                swiperChild.setBackgroundResource(R.drawable.shape_swiper_indicator_un_selected);
            }
        }
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

        // LogUtil.d(HomeCategoryDetailFragment.class, "categoryId = " + categoryId);
        // LogUtil.d(HomeCategoryDetailFragment.class, "categoryTitle = " + categoryTitle);

        // 请求分类数据
        homeCategoryDetailPresenter.getCategoryDetail(categoryId, 1);

        // 设置分类标题
        tvCategoryTitle.setText(categoryTitle);
    }

    @Override
    public void onCategoryDetailResult(CategoryDetail categoryDetail) {
        // LogUtil.d(HomeCategoryDetailFragment.class, "categoryDetail = " + categoryDetail);
        homeCategoryDetailAdapter.setCategoryDetailList(categoryDetail.getData());
    }

    @Override
    public void onCategoryDetailLoadMoreResult(CategoryDetail categoryDetail) {
        ToastUtil.show("成功加载了" + categoryDetail.getData().size() + "条数据!");
        homeCategoryDetailAdapter.addData(categoryDetail.getData());
        smartRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void onCategorySwiperResult(List<CategoryDetail.Data> smallImages) {
        this.swiperCountLength = smallImages.size();
        homeCategorySwiperAdapter.setData(smallImages);
        // 确保targetNum是smallImages.size()的倍数
        int targetNum = (Integer.MAX_VALUE / 2) - (Integer.MAX_VALUE / 2 % smallImages.size());
        // 将当前页置于中间
        if (categorySwiper != null) {
            categorySwiper.setCurrentItem(targetNum);
        }
        // 轮播图指示器容器
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
        ToastUtil.show("加载失败了，稍后再试试吧！");
        smartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.show("没有更多数据啦！");
        smartRefreshLayout.finishLoadMore(0, false, false);
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
        // stopLopperTask();
    }

    @Override
    protected void onRetry(View v) {
        //TODO: 需要考虑页码的问题
        homeCategoryDetailPresenter.getCategoryDetail(categoryId, 1);
    }
}
