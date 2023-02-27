package com.ilovesshan.couponunion.interfaces.presenter;

import com.ilovesshan.couponunion.base.BasePresenter;
import com.ilovesshan.couponunion.interfaces.view.ITicketViewCallback;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/26
 * @description:
 */
public interface ITicketPresenter extends BasePresenter<ITicketViewCallback> {
    void getTickCode(String title, String url);
}
