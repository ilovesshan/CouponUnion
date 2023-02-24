package com.ilovesshan.couponunion.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;
import com.ilovesshan.couponunion.utils.UrlUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/23
 * @description:
 */

public class HomeCategorySwiperAdapter extends PagerAdapter {
    private List<CategoryDetail.Data> swiperData = new ArrayList<>();

    @Override
    public int getCount() {
        // return swiperData.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final Context context = container.getContext();
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_home_category_swiper, container, false);

        // 实际的position
        int realPosition = position % swiperData.size();

        // 轮播图图片
        ImageView swiperImage = view.findViewById(R.id.swiper_image);
        final String optimizationImageUrl = UrlUtil.getOptimizationImageUrl(swiperData.get(realPosition).getPict_url(), 400);
        Glide.with(context).load(optimizationImageUrl).into(swiperImage);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setData(List<CategoryDetail.Data> swiperData) {
        this.swiperData.clear();
        this.swiperData.addAll(swiperData);
        notifyDataSetChanged();
    }
}
