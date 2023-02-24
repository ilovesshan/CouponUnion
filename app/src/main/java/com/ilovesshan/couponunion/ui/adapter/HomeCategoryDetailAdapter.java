package com.ilovesshan.couponunion.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;
import com.ilovesshan.couponunion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/23
 * @description:
 */
@SuppressLint("NonConstantResourceId")
public class HomeCategoryDetailAdapter extends RecyclerView.Adapter<HomeCategoryDetailAdapter.InnerHolder> {
    private List<CategoryDetail.Data> categoryDetailList = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(categoryDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryDetailList.size();
    }

    public void setCategoryDetailList(List<CategoryDetail.Data> categoryDetailList) {
        this.categoryDetailList.clear();
        this.categoryDetailList.addAll(categoryDetailList);
        notifyDataSetChanged();
    }

    /**
     * 新增新数据(加载更多)
     *
     * @param categoryDetailList categoryDetailList
     */
    public void addData(List<CategoryDetail.Data> categoryDetailList) {
        int oldIndex = this.categoryDetailList.size();
        this.categoryDetailList.addAll(categoryDetailList);
        notifyItemChanged(oldIndex, categoryDetailList.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_cover)
        public ImageView goodsCover;
        @BindView(R.id.goods_title)
        public TextView goodsTitle;
        @BindView(R.id.goods_reduce_money)
        public TextView goodsReduceMoney;
        @BindView(R.id.goods_reduce_after_money)
        public TextView goodsReduceAfterMoney;
        @BindView(R.id.goods_origin_money)
        public TextView goodsOriginMoney;
        @BindView(R.id.goods_sales_count)
        public TextView goodsSalesCount;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(CategoryDetail.Data data) {
            final float finalPrice = Float.parseFloat(data.getZk_final_price());
            final float couponAmount = data.getCoupon_amount();
            final float originPrice = (couponAmount + finalPrice);

            // 设置商品图片
            final ViewGroup.LayoutParams layoutParams = goodsCover.getLayoutParams();
            final int targetHeight = (Math.max(layoutParams.width, layoutParams.height)) / 2;
            final String optimizationImageUrl = UrlUtil.getOptimizationImageUrl(data.getPict_url(), targetHeight);
            Glide.with(itemView.getContext()).load(optimizationImageUrl).into(goodsCover);

            // 设置商品标题
            goodsTitle.setText(data.getTitle());

            // 设置立减价
            goodsReduceMoney.setText(String.format(itemView.getResources().getString(R.string.goods_reduce_money), couponAmount));

            // 设置最终价格
            goodsReduceAfterMoney.setText(String.format(itemView.getResources().getString(R.string.goods_reduce_after_money), finalPrice));

            // 设置原价
            goodsOriginMoney.setText(String.format(itemView.getResources().getString(R.string.goods_origin_price), originPrice));
            // 设置中划线和抗拒锯齿效果
            goodsOriginMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            goodsOriginMoney.getPaint().setAntiAlias(true);

            // 设置已售数量
            goodsSalesCount.setText(String.format(itemView.getResources().getString(R.string.goods_sales_count), data.getVolume()));
        }
    }
}
