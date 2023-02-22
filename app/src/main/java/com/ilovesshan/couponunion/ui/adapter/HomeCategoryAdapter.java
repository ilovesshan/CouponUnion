package com.ilovesshan.couponunion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ilovesshan.couponunion.model.entity.Categories;
import com.ilovesshan.couponunion.ui.fragment.HomeCategoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class HomeCategoryAdapter extends FragmentPagerAdapter {

    List<Categories.Data> categories = new ArrayList<>();

    public HomeCategoryAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new HomeCategoryFragment();
    }

    @Override
    public int getCount() {
        if (categories != null) {
            return categories.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (categories != null) {
            return categories.get(position).getTitle();
        }
        return super.getPageTitle(position);
    }

    public void setData(List<Categories.Data> data) {
        categories.clear();
        categories = data;
        notifyDataSetChanged();
    }
}
