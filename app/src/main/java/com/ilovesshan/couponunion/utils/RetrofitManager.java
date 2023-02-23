package com.ilovesshan.couponunion.utils;

import com.google.gson.Gson;
import com.ilovesshan.couponunion.config.ApiConfig;
import com.ilovesshan.couponunion.model.api.HomeApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public class RetrofitManager {
    private static RetrofitManager retrofitManager = null;
    private static Retrofit retrofit;

    private RetrofitManager() {
    }

    public static RetrofitManager getInstance() {
        synchronized (RetrofitManager.class) {
            if (retrofitManager == null) {
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }

    public Retrofit getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static <T> T getServiceApi(Class<T> tClass) {
        final Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        return retrofit.create(tClass);
    }
}
