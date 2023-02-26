package com.ilovesshan.couponunion.model;

import com.ilovesshan.couponunion.entity.Ticket;
import com.ilovesshan.couponunion.entity.TicketDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */
public interface TicketModel {
    @POST("tpwd")
    Call<Ticket> getTicketCode(@Body TicketDto TicketDto);
}
