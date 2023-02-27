package com.ilovesshan.couponunion.ui.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseApplication;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.entity.RecommendNav;
import com.ilovesshan.couponunion.entity.RecommendNavList;
import com.ilovesshan.couponunion.interfaces.view.IRecommendViewCallback;
import com.ilovesshan.couponunion.presenter.RecommendPresenter;
import com.ilovesshan.couponunion.ui.adapter.RecommendNavAdapter;
import com.ilovesshan.couponunion.utils.LogUtil;
import com.ilovesshan.couponunion.utils.PresenterManager;
import com.ilovesshan.couponunion.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
@SuppressLint("NonConstantResourceId")
public class RecommendFragment extends BaseFragment implements IRecommendViewCallback {

    @BindView(R.id.recommend_nav)
    public RecyclerView recommendNav;

    @BindView(R.id.state_empty_view)
    public RelativeLayout sateEmptyView;

    @BindView(R.id.state_empty_text)
    public TextView stateEmptyText;

    private RecommendPresenter recommendPresenter;
    private RecommendNavAdapter recommendNavAdapter;
    private RecommendNav.Data currentRecommendNav;

    @Override
    public int getResourceId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initPresenter() {

        stateEmptyText = sateEmptyView.findViewById(R.id.state_empty_text);


        // 获取presenter实例 和 注册 view回调接口
        recommendPresenter = PresenterManager.getRecommendPresenter();
        recommendPresenter.registerViewCallBack(this);

        // 设置适配器以及事件监听和布局管理器
        recommendNavAdapter = new RecommendNavAdapter();
        recommendNavAdapter.setOnItemClick(data -> {
            // 根据推荐ID 获取推荐列表
            recommendPresenter.getRecommendList(data.getFavorites_id());
            currentRecommendNav = data;
            showLoadRecommendNavListLoading();
        });
        recommendNav.setLayoutManager(new LinearLayoutManager(getContext()));
        recommendNav.setAdapter(recommendNavAdapter);

        // 获取推荐分类
        recommendPresenter.getRecommendNav();
    }

    /**
     * 模拟loading加载效果
     */
    private void showLoadRecommendNavListLoading() {
        ToastUtil.showLoading(RecommendFragment.this.getContext(), "正在努力加载中...", false);
        BaseApplication.getHandler().postDelayed(ToastUtil::dismissLoading, 500);
    }

    @Override
    public void onRecommendNavResult(RecommendNav recommendNav) {
        LogUtil.d(RecommendFragment.class, "recommendNav = " + recommendNav);
        currentRecommendNav = recommendNav.getData().get(0);
        recommendNavAdapter.setData(recommendNav.getData());

        // 根据推荐ID 获取推荐列表
        recommendPresenter.getRecommendList(currentRecommendNav.getFavorites_id());
        showLoadRecommendNavListLoading();
    }

    @Override
    public void onRecommendListResult(RecommendNavList recommendNavList) {
        // TODO: 后端接口问题 这里暂时定位暂无数据(在xml中写死了)
        LogUtil.d(RecommendFragment.class, "recommendNavList = " + recommendNavList);
        // if (recommendNavList.getData() != null && recommendNavList.getData().getTbk_uatm_favorites_item_get_response() != null) { }
        stateEmptyText.setText(String.format(getResources().getString(R.string.no_date_text), currentRecommendNav.getFavorites_title()));
    }

    @Override
    protected void onRetry(View v) {
        // 获取推荐分类
        recommendPresenter.getRecommendNav();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recommendPresenter.unRegisterViewCallBack(this);
    }
}