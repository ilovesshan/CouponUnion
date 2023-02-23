package com.ilovesshan.couponunion.presenter.impl;

import com.ilovesshan.couponunion.config.Constants;
import com.ilovesshan.couponunion.model.api.HomeApi;
import com.ilovesshan.couponunion.model.entity.CategoryDetail;
import com.ilovesshan.couponunion.presenter.IHomeCategoryDetailPresenter;
import com.ilovesshan.couponunion.utils.LogUtil;
import com.ilovesshan.couponunion.utils.RetrofitManager;
import com.ilovesshan.couponunion.view.IHomeCategoryDetailViewCallBack;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */

public class HomeCategoryDetailPresenter implements IHomeCategoryDetailPresenter {
    private static HomeCategoryDetailPresenter instance;
    /**
     * 保存UI引用的列表
     */
    private final List<IHomeCategoryDetailViewCallBack> homeCategoryDetailViewCallBacks = new ArrayList<>();

    /**
     * 默认页码
     */
    private final int DEFAULT_PAGE = 1;

    /**
     * 保存当前页面信息(分类ID:请求页码数)
     */
    private Map<Integer, Integer> currentPageInfo = new HashMap<>();
    private Integer currentPage;

    private HomeCategoryDetailPresenter() {
    }

    public static HomeCategoryDetailPresenter getInstance() {
        if (instance == null) {
            instance = new HomeCategoryDetailPresenter();
        }
        return instance;
    }

    @Override
    public void getCategoryDetail(int categoryId, int page) {
        // 根据分类ID获取当前类别的页码
        Integer currentPage = currentPageInfo.get(categoryId);
        if (currentPage == null) {
            currentPage = DEFAULT_PAGE;
            currentPageInfo.put(categoryId, currentPage);
        }
        // loading加载中
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                callBack.onLoading();
            }
        }
        RetrofitManager.getServiceApi(HomeApi.class).getCategoryDetail(categoryId, currentPage).enqueue(new Callback<CategoryDetail>() {
            @Override
            public void onResponse(Call<CategoryDetail> call, Response<CategoryDetail> response) {
                final int code = response.code();
                LogUtil.d(HomeCategoryDetailPresenter.class, "code = " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    if (response.body() != null && response.body().getData().size() > 0) {
                        LogUtil.d(HomePresenter.class, "response.body() = " + response.body());
                        // 请求成功
                        handleLoadSuccess(response, categoryId);
                    } else {
                        // 请求数据失败
                        handleLoadEmpty(categoryId);
                    }
                } else {
                    // 请求数据失败
                    handleLoadError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<CategoryDetail> call, Throwable t) {
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
                // 请求数据
                handleLoadError(categoryId);
            }
        });
    }

    /**
     * 处理数据请求失败的情况
     *
     * @param categoryId 分类ID
     */
    private void handleLoadError(int categoryId) {
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                callBack.onError();
            }
        }
    }

    /**
     * 处理数据请求为空的情况
     *
     * @param categoryId 分类ID
     */
    private void handleLoadEmpty(int categoryId) {
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                callBack.onEmpty();
            }
        }
    }

    /**
     * 处理数据请求成功的情况
     *
     * @param categoryId 分类ID
     */
    private void handleLoadSuccess(Response<CategoryDetail> response, int categoryId) {
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                callBack.onSuccess();
                // 返回分类列表
                callBack.onCategoryDetailResult(response.body());
                // 返回分类轮播图
                final List<CategoryDetail.Data> dataList = response.body().getData();
                // 返回最后五条数据
                callBack.onCategorySwiperResult(dataList.subList(dataList.size() - Constants.SWIPER_COUNT_LENGTH, dataList.size()));
            }
        }
    }


    /**
     * 处理数据请求加载更多失败的情况
     *
     * @param categoryId 分类ID
     */
    private void handleLoadMoreError(int categoryId) {
        // 如果加载失败了，需要将页码减一
        currentPage--;
        currentPageInfo.put(categoryId, currentPage);
        // 通知UI更新
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                callBack.onLoadMoreError();
            }
        }
    }

    /**
     * 处理数据请求加载更多为空的情况
     *
     * @param categoryId 分类ID
     */
    private void handleLoadMoreEmpty(int categoryId) {
        // 如果加载为空，需要将页码减一
        currentPage--;
        currentPageInfo.put(categoryId, currentPage);
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                callBack.onLoadMoreEmpty();
            }
        }
    }

    /**
     * 处理数据请求加载更多成功的情况
     *
     * @param categoryId 分类ID
     */
    private void handleLoadMoreSuccess(Response<CategoryDetail> response, int categoryId) {
        for (IHomeCategoryDetailViewCallBack callBack : homeCategoryDetailViewCallBacks) {
            if (callBack.getCurrentCategoryId() == categoryId) {
                // 返回分类列表
                callBack.onCategoryDetailLoadMoreResult(response.body());
            }
        }
    }


    /**
     * 下拉刷新 加载更多
     *
     * @param categoryId 分类ID
     */
    @Override
    public void loadMore(int categoryId) {
        currentPage = currentPageInfo.get(categoryId);
        if (currentPage == null) {
            currentPage = DEFAULT_PAGE;
        }
        currentPage++;
        currentPageInfo.put(categoryId, currentPage);
        LogUtil.d(HomeCategoryDetailPresenter.class, "categoryId = " + categoryId + ", currentPage = " + currentPage);
        RetrofitManager.getServiceApi(HomeApi.class).getCategoryDetail(categoryId, currentPage).enqueue(new Callback<CategoryDetail>() {
            @Override
            public void onResponse(Call<CategoryDetail> call, Response<CategoryDetail> response) {
                final int code = response.code();
                LogUtil.d(HomeCategoryDetailPresenter.class, "code = " + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    if (response.body() != null && response.body().getData().size() > 0) {
                        LogUtil.d(HomePresenter.class, "response.body() = " + response.body());
                        // 请求成功
                        handleLoadMoreSuccess(response, categoryId);
                    } else {
                        // 请求数据为空
                        handleLoadMoreEmpty(categoryId);
                    }
                } else {
                    // 请求数据失败
                    handleLoadMoreError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<CategoryDetail> call, Throwable t) {
                LogUtil.d(HomePresenter.class, "请求失败: " + t.getMessage());
                t.printStackTrace();
                // 请求数据
                handleLoadMoreError(categoryId);
            }
        });
    }

    @Override
    public void refresh(int categoryId, int page) {

    }

    @Override
    public void registerViewCallBack(IHomeCategoryDetailViewCallBack callback) {
        if (!this.homeCategoryDetailViewCallBacks.contains(callback)) {
            this.homeCategoryDetailViewCallBacks.add(callback);
        }
    }

    @Override
    public void unRegisterViewCallBack(IHomeCategoryDetailViewCallBack callback) {
        this.homeCategoryDetailViewCallBacks.remove(callback);
    }
}
