package com.ilovesshan.couponunion.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ilovesshan.couponunion.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/22
 * @description:
 */

@SuppressLint("NonConstantResourceId")
public abstract class BaseFragment extends Fragment implements BaseViewCallback {

    public enum ViewState {
        NONE, LOADING, SUCCESS, EMPTY, ERROR
    }

    private ViewState currentViewState = ViewState.NONE;
    private FrameLayout baseViewContainer;

    private View loadingView;
    private View emptyView;
    private View errorView;
    private View successView;

    private Unbinder unbinder;


    @OnClick(R.id.retry_container)
    public void retryClick(View v) {
        onRetry(v);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 通过baseView根据状态来显示不同的界面(加载中、加载失败、加载成功、内容为空...)
        final View baseView = loadBaseView(inflater, container);
        baseViewContainer = baseView.findViewById(R.id.base_view_container);
        loadBaseViewState(inflater, container);

        unbinder = ButterKnife.bind(this, baseView);

        // 初始化view和绑定事件
        initViewAndBindEvent();

        // 初始化 presenter
        initPresenter();

        // 加载数据
        loadData();

        return baseView;
    }

    /**
     * 将各种状态的View放入到baseViewContainer中， 后期通过updateViewState() 函数控制显示和隐藏
     *
     * @param inflater  inflater
     * @param container container
     */
    private void loadBaseViewState(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        loadingView = loadLoadingView(inflater, container);
        baseViewContainer.addView(loadingView);

        emptyView = loadEmptyView(inflater, container);
        baseViewContainer.addView(emptyView);

        errorView = loadErrorView(inflater, container);
        baseViewContainer.addView(errorView);

        successView = loadSuccessView(inflater, container);
        baseViewContainer.addView(successView);

        // 默认全部隐藏掉
        updateViewState(currentViewState);
    }


    /**
     * 加载 baseView, 子类复写时可以自己定制view
     *
     * @param inflater  inflater
     * @param container container
     * @return
     */
    protected View loadBaseView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.state_base_view, container, false);
    }

    /**
     * 初始化view和绑定事件
     */
    protected void initViewAndBindEvent() {
    }

    /**
     * 初始化 presenter
     */
    protected void initPresenter() {
    }


    /**
     * 加载数据
     */
    protected void loadData() {
    }

    /**
     * 根据不同的状态显示不同的View
     *
     * @param viewState viewState
     */
    protected void updateViewState(ViewState viewState) {
        this.currentViewState = viewState;
        loadingView.setVisibility(viewState == ViewState.LOADING ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(viewState == ViewState.EMPTY ? View.VISIBLE : View.GONE);
        errorView.setVisibility(viewState == ViewState.ERROR ? View.VISIBLE : View.GONE);
        successView.setVisibility(viewState == ViewState.SUCCESS ? View.VISIBLE : View.GONE);
    }

    /**
     * 数据请求成功显示的UI
     */
    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resourcesId = getResourceId();
        return inflater.inflate(resourcesId, container, false);
    }

    /**
     * 数据请求失败显示的UI
     */
    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.state_error_view, container, false);
    }


    /**
     * 数据请求中显示的UI
     */
    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.state_loading_view, container, false);
    }

    /**
     * 数据请求为空显示的UI
     */
    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.state_empty_view, container, false);

    }

    protected abstract int getResourceId();


    /**
     * 加载失败时, 点击重新加载
     *
     * @param v view对象
     */
    protected void onRetry(View v) {
    }


    @Override
    public void onError() {
        updateViewState(ViewState.ERROR);
    }

    @Override
    public void onEmpty() {
        updateViewState(ViewState.EMPTY);
    }

    @Override
    public void onLoading() {
        updateViewState(ViewState.LOADING);
    }

    @Override
    public void onSuccess() {
        updateViewState(ViewState.SUCCESS);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
