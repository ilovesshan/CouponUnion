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
import com.ilovesshan.couponunion.entity.Special;
import com.ilovesshan.couponunion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */

@SuppressLint("NonConstantResourceId")
public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.InnerHolder> {
    private OnItemClick onItemClick;
    private List<Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData> data = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        final Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData data = this.data.get(position);
        holder.setData(data);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData> data) {
        final int size = this.data.size();
        this.data.addAll(data);
        notifyItemChanged(size, data.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_cover)
        public ImageView goodsCover;

        @BindView(R.id.goods_title)
        public TextView goodsTitle;

        @BindView(R.id.goods_origin_price)
        public TextView goodsOriginPrice;

        @BindView(R.id.goods_reduce_after_price)
        public TextView goodsReduceAfterPrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("StringFormatMatches")
        public void setData(Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData data) {
            // 图片
            Glide.with(itemView.getContext()).load(UrlUtil.getOptimizationImageUrl(data.getPict_url(), 200)).into(goodsCover);

            // 标题
            goodsTitle.setText(data.getTitle());

            final float commonAmount = Float.parseFloat(String.valueOf(data.getCoupon_amount()));
            final float finalPrice = Float.parseFloat(data.getZk_final_price());

            // 原价
            goodsOriginPrice.setText(String.format(itemView.getResources().getString(R.string.goods_origin_price), commonAmount + finalPrice));
            goodsOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            goodsOriginPrice.getPaint().setAntiAlias(true);

            // 最终价
            goodsReduceAfterPrice.setText(String.format(itemView.getResources().getString(R.string.goods_reduce_after_money), (Float.parseFloat(data.getZk_final_price()) - commonAmount)));
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void removeOnItemClick() {
        this.onItemClick = null;
    }

    public interface OnItemClick {
        public void onClick(Special.Data.TbkDgOptimusMaterialResponse.ResultList.MapData data);
    }
}
