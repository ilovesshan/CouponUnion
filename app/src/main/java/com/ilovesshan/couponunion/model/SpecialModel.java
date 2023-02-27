package com.ilovesshan.couponunion.model;

import com.ilovesshan.couponunion.entity.Special;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface SpecialModel {
    @GET("onSell/{page}")
    Call<Special> getSpecial(@Path("page") int page);
}
