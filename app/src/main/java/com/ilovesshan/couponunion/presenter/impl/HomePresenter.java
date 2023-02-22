package com.ilovesshan.couponunion.presenter.impl;

import com.ilovesshan.couponunion.model.api.HomeApi;
import com.ilovesshan.couponunion.model.entity.Categories;
import com.ilovesshan.couponunion.presenter.IHomePresenter;
import com.ilovesshan.couponunion.ui.fragment.HomeFragment;
import com.ilovesshan.couponunion.utils.LogUtil;
import com.ilovesshan.couponunion.utils.RetrofitManager;
import com.ilovesshan.couponunion.view.IHomeViewCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class HomePresenter implements IHomePresenter {
    private IHomeViewCallback homeViewCallback;

    @Override
    public void getCategories() {
        final Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        retrofit.create(HomeApi.class).getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                final int code = response.code();
                LogUtil.d(HomePresenter.class, "code = " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    if (homeViewCallback != null) {
                        homeViewCallback.onCategoriesResult(response.body());
                        LogUtil.d(HomePresenter.class, "response.body() = " + response.body());
                    }
                } else {
                    LogUtil.d(HomePresenter.class, "请求失败: code = " + code + ", message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void registerViewCallBack(IHomeViewCallback homeViewCallback) {
        this.homeViewCallback = homeViewCallback;
    }

    @Override
    public void unRegisterViewCallBack(IHomeViewCallback iHomeViewCallback) {
        if (homeViewCallback != null) {
            homeViewCallback = null;
        }
    }
}
