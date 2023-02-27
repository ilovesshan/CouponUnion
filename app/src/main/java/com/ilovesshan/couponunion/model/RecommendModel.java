
package com.ilovesshan.couponunion.model;

import com.ilovesshan.couponunion.entity.RecommendNav;
import com.ilovesshan.couponunion.entity.RecommendNavList;

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
public interface RecommendModel {
    @GET("recommend/categories")
    Call<RecommendNav> getRecommendNav();


    @GET("recommend/{categoryId}")
    Call<RecommendNavList> getRecommendNavList(@Path("categoryId") int categoryId);
}
