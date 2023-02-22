package com.ilovesshan.couponunion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = loadSubView(inflater, container, savedInstanceState);

        unbinder = ButterKnife.bind(this, view);

        // 初始化view和绑定事件
        initViewAndBindEvent();

        // 初始化 presenter
        initPresenter();

        // 加载数据
        loadData();

        return view;
    }

    /**
     * 初始化view和绑定事件
     */
    protected void initViewAndBindEvent() {
    }

    /**
     * 初始化 presenter
     */
    protected void initPresenter() {
    }


    /**
     * 加载数据
     */
    protected void loadData() {
    }


    private View loadSubView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        int resourcesId = getResourceId();
        return inflater.inflate(resourcesId, container, false);
    }

    public abstract int getResourceId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
