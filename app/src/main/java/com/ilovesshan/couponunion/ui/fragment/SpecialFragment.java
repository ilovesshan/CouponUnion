package com.ilovesshan.couponunion.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.config.ApiConfig;
import com.ilovesshan.couponunion.entity.Special;
import com.ilovesshan.couponunion.interfaces.view.ISpecialViewCallback;
import com.ilovesshan.couponunion.presenter.SpecialPresenter;
import com.ilovesshan.couponunion.ui.activites.TicketActivity;
import com.ilovesshan.couponunion.ui.adapter.SpecialAdapter;
import com.ilovesshan.couponunion.utils.PresenterManager;
import com.ilovesshan.couponunion.utils.ScreenUtil;
import com.ilovesshan.couponunion.utils.ToastUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

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
public class SpecialFragment extends BaseFragment implements ISpecialViewCallback {

    @BindView(R.id.special_list)
    public RecyclerView specialList;

    @BindView(R.id.refresh_layout)
    public SmartRefreshLayout smartRefreshLayout;

    private SpecialPresenter specialPresenter;
    private SpecialAdapter specialAdapter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_special;
    }

    @Override
    protected void initViewAndBindEvent() {
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.finishRefresh(2000));
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> specialPresenter.loadMoreSpecialData());

        // 设置适配器以及适配器事件监听和布局管理
        specialAdapter = new SpecialAdapter();
        specialAdapter.setOnItemClick(data -> {
            final String targetUrl = TextUtils.isEmpty(data.getCoupon_click_url()) ? data.getClick_url() : data.getCoupon_click_url();
            final Intent intent = new Intent(getContext(), TicketActivity.class);
            intent.putExtra("title", data.getTitle());
            intent.putExtra("clickUrl", ApiConfig.PROTOCOL + targetUrl);
            intent.putExtra("cover", ApiConfig.PROTOCOL + data.getPict_url());
            startActivity(intent);
        });
        specialList.setAdapter(specialAdapter);
        specialList.setLayoutManager(new GridLayoutManager(SpecialFragment.this.getContext(), 2));
        specialList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                final int dipValue = ScreenUtil.dip2px(SpecialFragment.this.getContext(), 5);
                outRect.top = dipValue;
                outRect.right = dipValue;
                outRect.bottom = dipValue;
                outRect.left = dipValue;
            }
        });

        specialPresenter = PresenterManager.getInstance().getSpecialPresenter();
        specialPresenter.registerViewCallBack(this);
        specialPresenter.getSpecialData();
    }

    @Override
    public void onSpecialDataResult(Special special) {
        final List<Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData> map_data = special.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        specialAdapter.setData(map_data);
    }

    @Override
    public void onLoadMoreSpecialDataResult(Special special) {
        final List<Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData> map_data = special.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data();
        ToastUtil.showMessage("成功加载" + map_data.size() + "条数据!");
        smartRefreshLayout.finishLoadMore(true);
        specialAdapter.addData(map_data);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtil.showMessage("没有更多数据了，已经到底啦！");
        smartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtil.showMessage("加载失败，请稍后再试!");
        smartRefreshLayout.finishLoadMore(0, true, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (specialPresenter != null) {
            specialPresenter.unRegisterViewCallBack(this);
        }
    }
}