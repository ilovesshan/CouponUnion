package com.ilovesshan.couponunion.presenter;

import com.google.gson.Gson;
import com.ilovesshan.couponunion.entity.RecommendNav;
import com.ilovesshan.couponunion.entity.RecommendNavList;
import com.ilovesshan.couponunion.interfaces.presenter.IRecommendPresenter;
import com.ilovesshan.couponunion.interfaces.view.IRecommendViewCallback;
import com.ilovesshan.couponunion.model.RecommendModel;
import com.ilovesshan.couponunion.utils.LogUtil;
import com.ilovesshan.couponunion.utils.RetrofitManager;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */
public class RecommendPresenter implements IRecommendPresenter {
    private IRecommendViewCallback callback;

    @Override
    public void getRecommendNav() {
        if (callback != null) {
            callback.onLoading();
        }
        final Call<RecommendNav> recommendNav = RetrofitManager.getServiceApi(RecommendModel.class).getRecommendNav();
        recommendNav.enqueue(new Callback<RecommendNav>() {
            @Override
            public void onResponse(Call<RecommendNav> call, Response<RecommendNav> response) {
                // 后端接口有问题 这里展示写死数据
                String mockData = "{\"success\":true,\"code\":10000,\"message\":\"获取精选分类成功.\",\"data\":[{\"type\":1,\"favorites_id\":19876595,\"favorites_title\":\"程序员必备\"},{\"type\":1,\"favorites_id\":19876636,\"favorites_title\":\"办公室零食\"},{\"type\":1,\"favorites_id\":19876637,\"favorites_title\":\"上班族早餐\"},{\"type\":1,\"favorites_id\":19876649,\"favorites_title\":\"日用品\"},{\"type\":1,\"favorites_id\":19902751,\"favorites_title\":\"电脑周边\"},{\"type\":1,\"favorites_id\":19903201,\"favorites_title\":\"秋天必备\"}]}";
                final RecommendNav responseData = new Gson().fromJson(mockData, RecommendNav.class);
                if (callback != null) {
                    callback.onSuccess();
                    callback.onRecommendNavResult(responseData);
                }
            }

            @Override
            public void onFailure(Call<RecommendNav> call, Throwable t) {
                if (callback != null) {
                    callback.onError();
                }
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void getRecommendList(int categoryId) {
        final Call<RecommendNavList> recommendNav = RetrofitManager.getServiceApi(RecommendModel.class).getRecommendNavList(categoryId);
        recommendNav.enqueue(new Callback<RecommendNavList>() {
            @Override
            public void onResponse(Call<RecommendNavList> call, Response<RecommendNavList> response) {
                final int code = response.code();
                LogUtil.d(RecommendPresenter.class, "code = " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    LogUtil.d(RecommendPresenter.class, " response.body()" + response.body());
                    if (callback != null) {
                        callback.onRecommendListResult(response.body());
                    }
                } else {
                    LogUtil.d(HomePresenter.class, "请求失败: code = " + code + ", message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RecommendNavList> call, Throwable t) {
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void registerViewCallBack(IRecommendViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void unRegisterViewCallBack(IRecommendViewCallback callback) {
        this.callback = null;
    }
}
