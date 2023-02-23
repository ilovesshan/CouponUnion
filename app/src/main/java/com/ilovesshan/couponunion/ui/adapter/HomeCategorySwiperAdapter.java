package com.ilovesshan.couponunion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.config.ApiConfig;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;

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
        return swiperData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_category_swiper, container, false);
        ImageView imageView = view.findViewById(R.id.swiper_image);
        Glide.with(container.getContext()).load(ApiConfig.PROTOCOL + swiperData.get(position).getPict_url()).into(imageView);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setData(List<CategoryDetail.Data> images) {
        this.swiperData.clear();
        this.swiperData.addAll(images);
        notifyDataSetChanged();
    }
}
