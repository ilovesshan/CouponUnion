package com.ilovesshan.couponunion.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.base.BaseApplication;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2022/5/27
 * @description: UI加载器 根据状态加载不同界面  加载中/成功/失败/数据为空...
 */
public abstract class UILoader extends FrameLayout {

    // 默认状态
    public static UILoaderState sUILoaderState = UILoaderState.NONE;

    private View mLoadingView;
    private View mLoadSuccessView;
    private View mLoadErrorView;
    private View mLoadEmptyView;

    /**
     * 页面加载状态
     */
    public static enum UILoaderState {
        LOADING, SUCCESS, ERROR, EMPTY, NONE
    }


    OnRetryLoadClickListener mOnRetryLoadClickListener = null;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // 加载界面
        updateUIByState();
    }


    public void updateUILoaderState(UILoaderState uiLoaderState) {
        // 更正UIState 的状态
        sUILoaderState = uiLoaderState;
        BaseApplication.getHandler().post(() -> updateUIByState());
    }

    /**
     * 更具State 更新UI界面
     */
    private void updateUIByState() {
        // 控制UI的显示和隐藏

        // 加载中
        if (mLoadingView == null) {
            mLoadingView = createLoadingView();
            addView(mLoadingView);
        }
        mLoadingView.setVisibility(sUILoaderState == UILoaderState.LOADING ? VISIBLE : GONE);

        // 加载成功
        if (mLoadSuccessView == null) {
            mLoadSuccessView = createSuccessView();
            addView(mLoadSuccessView);
        }
        mLoadSuccessView.setVisibility(sUILoaderState == UILoaderState.SUCCESS ? VISIBLE : GONE);

        // 加载失败
        if (mLoadErrorView == null) {
            mLoadErrorView = createErrorView();
            addView(mLoadErrorView);
        }
        mLoadErrorView.setVisibility(sUILoaderState == UILoaderState.ERROR ? VISIBLE : GONE);

        // 加载 空数据
        if (mLoadEmptyView == null) {
            mLoadEmptyView = createEmptyView();
            addView(mLoadEmptyView);
        }
        mLoadEmptyView.setVisibility(sUILoaderState == UILoaderState.EMPTY ? VISIBLE : GONE);
    }


    /**
     * 创建加载中的View
     */
    private View createLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.state_loading_view, this, false);
    }

    /**
     * 创建加载成功的View
     */
    protected abstract View createSuccessView();

    /**
     * 创建加载失败的View
     */
    private View createErrorView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.state_error_view, this, false);

        // 处理加载失败 点击重新加载
        View loadErrorContainer = view.findViewById(R.id.retry_container);

        loadErrorContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryLoadClickListener != null) {
                    mOnRetryLoadClickListener.onRetry();
                }
            }
        });

        return view;

    }

    /**
     * 创建空数据的View
     */
    private View createEmptyView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.state_empty_view, this, false);
    }

    public void setOnRetryLoadClickListener(OnRetryLoadClickListener listener) {
        this.mOnRetryLoadClickListener = listener;
    }

    public interface OnRetryLoadClickListener {
        void onRetry();
    }

}