package com.ilovesshan.couponunion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.entity.RecommendNav;

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
public class RecommendNavAdapter extends RecyclerView.Adapter<RecommendNavAdapter.InnerHolder> {
    private List<RecommendNav.Data> recommendNavs = new ArrayList<>();
    private OnItemClick onItemClick;

    private int currentIndex = 0;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_nav, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        final RecommendNav.Data data = recommendNavs.get(position);
        holder.setData(data);

        if (currentIndex == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.white));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.efefef));
        }

        holder.itemView.setOnClickListener(v -> {
            if (position != currentIndex && onItemClick != null) {
                currentIndex = position;
                onItemClick.onClick(data);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendNavs.size();
    }

    public void setData(List<RecommendNav.Data> recommendNavs) {
        this.recommendNavs = recommendNavs;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        public TextView recommendNavTitle;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            recommendNavTitle = itemView.findViewById(R.id.recommend_nav_title);
        }

        public void setData(RecommendNav.Data data) {
            recommendNavTitle.setText(data.getFavorites_title());
        }
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void removeOnItemClick() {
        this.onItemClick = null;
    }

    public interface OnItemClick {
        public void onClick(RecommendNav.Data data);
    }
}
