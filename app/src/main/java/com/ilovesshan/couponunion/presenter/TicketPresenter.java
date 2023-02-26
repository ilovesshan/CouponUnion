package com.ilovesshan.couponunion.presenter;

import com.ilovesshan.couponunion.base.BaseFragment;
import com.ilovesshan.couponunion.entity.Ticket;
import com.ilovesshan.couponunion.entity.TicketDto;
import com.ilovesshan.couponunion.interfaces.presenter.ITicketPresenter;
import com.ilovesshan.couponunion.interfaces.view.ITicketViewCallback;
import com.ilovesshan.couponunion.model.TicketModel;
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
 * @date: 2023/2/26
 * @description:
 */
public class TicketPresenter implements ITicketPresenter {
    private ITicketViewCallback callback;
    private Ticket responseData;
    private BaseFragment.ViewState currentSate = BaseFragment.ViewState.NONE;

    @Override
    public void getTickCode(String title, String url) {
        currentSate = BaseFragment.ViewState.LOADING;
        if (callback != null) {
            callback.onLoading();
        }
        final TicketDto ticketDto = new TicketDto(url, title);
        final Call<Ticket> ticketCall = RetrofitManager.getServiceApi(TicketModel.class).getTicketCode(ticketDto);
        ticketCall.enqueue(new Callback<Ticket>() {

            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                final int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    LogUtil.d(TicketPresenter.class, "response.body = " + response.body());
                    responseData = response.body();
                    currentSate = BaseFragment.ViewState.SUCCESS;
                    if (callback != null) {
                        callback.onSuccess();
                        callback.onTickCodeResult(response.body());
                    }
                } else {
                    if (callback != null) {
                        callback.onError();
                    }
                    currentSate = BaseFragment.ViewState.ERROR;
                    LogUtil.d(HomePresenter.class, "请求失败: code = " + code + ", message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
                if (callback != null) {
                    callback.onError();
                }
                currentSate = BaseFragment.ViewState.ERROR;
            }
        });
    }

    @Override
    public void registerViewCallBack(ITicketViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public void unRegisterViewCallBack(ITicketViewCallback callback) {
        this.callback = null;
    }
}
