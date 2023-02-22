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
        if (homeViewCallback != null) {
            // 开始加载 显示loading
            homeViewCallback.onLoading();

            final Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
            retrofit.create(HomeApi.class).getCategories().enqueue(new Callback<Categories>() {
                @Override
                public void onResponse(Call<Categories> call, Response<Categories> response) {
                    final int code = response.code();
                    LogUtil.d(HomePresenter.class, "code = " + code);
                    if (code == HttpURLConnection.HTTP_OK) {
                        if (response.body() != null && response.body().getData().size() > 0) {
                            LogUtil.d(HomePresenter.class, "response.body() = " + response.body());
                            homeViewCallback.onSuccess();
                            homeViewCallback.onCategoriesResult(response.body());
                        } else {
                            homeViewCallback.onEmpty();
                        }
                    } else {
                        homeViewCallback.onError();
                        LogUtil.d(HomePresenter.class, "请求失败: code = " + code + ", message = " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Categories> call, Throwable t) {
                    homeViewCallback.onError();
                    LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                    t.printStackTrace();
                }
            });
        }
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
