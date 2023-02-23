package com.ilovesshan.couponunion.model.api;

import com.ilovesshan.couponunion.model.entity.Category;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;

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
public interface HomeApi {
    @GET("discovery/categories")
    Call<Category> getCategory();

    @GET("discovery/{materialId}/{page}")
    Call<CategoryDetail> getCategoryDetail(@Path("materialId") int materialId, @Path("page") int page);
}
