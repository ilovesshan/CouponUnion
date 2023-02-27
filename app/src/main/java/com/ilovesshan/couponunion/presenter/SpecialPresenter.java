package com.ilovesshan.couponunion.presenter;

import com.ilovesshan.couponunion.config.ApiConfig;
import com.ilovesshan.couponunion.entity.Special;
import com.ilovesshan.couponunion.interfaces.presenter.ISpecialPresenter;
import com.ilovesshan.couponunion.interfaces.view.ISpecialViewCallback;
import com.ilovesshan.couponunion.model.SpecialModel;
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
public class SpecialPresenter implements ISpecialPresenter {

    private ISpecialViewCallback callback;
    private int currentPage = ApiConfig.REQUEST_DEFAULT_PAGE;

    @Override
    public void getSpecialData() {
        if (callback != null) {
            callback.onLoading();
        }
        final Call<Special> ticketCall = RetrofitManager.getServiceApi(SpecialModel.class).getSpecial(currentPage);
        ticketCall.enqueue(new Callback<Special>() {
            @Override
            public void onResponse(Call<Special> call, Response<Special> response) {
                final int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    final Special special = response.body();
                    LogUtil.d(SpecialPresenter.class, "response.body() = " + response.body());
                    if (callback != null) {
                        callback.onSuccess();
                        callback.onSpecialDataResult(special);
                    }
                } else {
                    if (callback != null) {
                        callback.onError();
                    }
                    LogUtil.d(HomePresenter.class, "请求失败: code = " + code + ", message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Special> call, Throwable t) {
                if (callback != null) {
                    callback.onError();
                }
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadMoreSpecialData() {
        currentPage++;
        final Call<Special> ticketCall = RetrofitManager.getServiceApi(SpecialModel.class).getSpecial(currentPage);
        ticketCall.enqueue(new Callback<Special>() {
            @Override
            public void onResponse(Call<Special> call, Response<Special> response) {
                final int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    final Special special = response.body();
                    LogUtil.d(SpecialPresenter.class, "response.body() = " + response.body());
                    if (special != null && special.getData().getTbk_dg_optimus_material_response() != null && special.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size() > 0) {
                        if (callback != null) {
                            callback.onLoadMoreSpecialDataResult(special);
                        }
                    } else {
                        currentPage--;
                        if (callback != null) {
                            callback.onLoadMoreEmpty();
                        }
                    }
                } else {
                    currentPage--;
                    if (callback != null) {
                        callback.onLoadMoreError();
                    }
                    LogUtil.d(HomePresenter.class, "请求失败: code = " + code + ", message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Special> call, Throwable t) {
                currentPage--;
                if (callback != null) {
                    callback.onLoadMoreError();
                }
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public void registerViewCallBack(ISpecialViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void unRegisterViewCallBack(ISpecialViewCallback callback) {
        this.callback = null;
    }
}
