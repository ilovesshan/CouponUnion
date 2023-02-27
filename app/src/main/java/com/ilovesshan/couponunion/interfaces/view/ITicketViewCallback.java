package com.ilovesshan.couponunion.interfaces.view;

import com.ilovesshan.couponunion.base.BaseViewCallback;
import com.ilovesshan.couponunion.entity.Ticket;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/26
 * @description:
 */
public interface ITicketViewCallback extends BaseViewCallback {
    void onTickCodeResult(Ticket ticket);
}
