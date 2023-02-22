package com.ilovesshan.couponunion.model.api;

import com.ilovesshan.couponunion.model.entity.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface HomeApi {
    @GET("discovery/categories")
    Call<Categories> getCategories();
}
