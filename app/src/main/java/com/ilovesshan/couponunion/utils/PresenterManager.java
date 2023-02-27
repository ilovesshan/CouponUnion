package com.ilovesshan.couponunion.utils;

import com.ilovesshan.couponunion.presenter.HomeCategoryDetailPresenter;
import com.ilovesshan.couponunion.presenter.HomePresenter;
import com.ilovesshan.couponunion.presenter.RecommendPresenter;
import com.ilovesshan.couponunion.presenter.SpecialPresenter;
import com.ilovesshan.couponunion.presenter.TicketPresenter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/27
 * @description:
 */
public class PresenterManager {
    private static PresenterManager PresenterManager = null;

    private static HomeCategoryDetailPresenter homeCategoryDetailPresenter;
    private static HomePresenter homePresenter;
    private static TicketPresenter ticketPresenter;
    private static RecommendPresenter recommendPresenter;
    private static SpecialPresenter specialPresenter;

    private PresenterManager() {
    }

    public static PresenterManager getInstance() {
        synchronized (PresenterManager.class) {
            if (PresenterManager == null) {
                PresenterManager = new PresenterManager();
                homeCategoryDetailPresenter = new HomeCategoryDetailPresenter();
                homePresenter = new HomePresenter();
                ticketPresenter = new TicketPresenter();
                recommendPresenter = new RecommendPresenter();
                specialPresenter = new SpecialPresenter();
            }
        }
        return PresenterManager;
    }

    public HomeCategoryDetailPresenter getHomeCategoryDetailPresenter() {
        return homeCategoryDetailPresenter;
    }

    public HomePresenter getHomePresenter() {
        return homePresenter;
    }

    public TicketPresenter getTicketPresenter() {
        return ticketPresenter;
    }

    public  RecommendPresenter getRecommendPresenter() { return recommendPresenter; }

    public  SpecialPresenter getSpecialPresenter() { return specialPresenter; }
}
