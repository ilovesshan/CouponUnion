package com.ilovesshan.couponunion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ilovesshan.couponunion.model.entity.Category;
import com.ilovesshan.couponunion.ui.fragment.HomeCategoryDetailFragment;

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

    List<Category.Data> categories = new ArrayList<>();

    public HomeCategoryAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        final Category.Data data = categories.get(position);
        // 将当前Pager项的分类id和标题传递给HomeCategoryFragment, 通过HomeCategoryFragment 去请求并展示数据
        return HomeCategoryDetailFragment.getHomeCategoryFragment(data.getId(), data.getTitle());
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

    public void setData(List<Category.Data> data) {
        categories.clear();
        categories.addAll(data);
        notifyDataSetChanged();
    }
}
