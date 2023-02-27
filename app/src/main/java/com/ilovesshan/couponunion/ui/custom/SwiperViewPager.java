package com.ilovesshan.couponunion.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.ilovesshan.couponunion.R;
import com.ilovesshan.couponunion.config.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/26
 * @description:
 */
public class SwiperViewPager extends ViewPager {
    private TimerTask timerTask;
    private Timer timer;
    private long duration = Constants.SWIPER_AUTO_LOPPER_DURATION;
    private boolean autoLopper;

    public SwiperViewPager(@NonNull Context context) {
        super(context);
    }

    public SwiperViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // 读取自定义属性
        readCustomProperties(context, attrs);
    }

    private void readCustomProperties(Context context, AttributeSet attrs) {
        final TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SwiperViewPager);
        final boolean autoLopper = styledAttributes.getBoolean(R.styleable.SwiperViewPager_auto_lopper, Constants.SWIPER_AUTO_LOPPER);
        final int duration = styledAttributes.getInteger(R.styleable.SwiperViewPager_duration, (int) Constants.SWIPER_AUTO_LOPPER_DURATION);

        this.autoLopper = autoLopper;
        this.duration = duration;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (autoLopper) {
            startLopperTask();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (autoLopper) {
            stopLopperTask();
        }
    }

    /**
     * 开启定时器任务 进行轮播图自动轮播
     */
    private void startLopperTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                int currentItem = getCurrentItem();
                setCurrentItem(++currentItem);
                postDelayed(this, duration);
            }
        };
        timer = new Timer();
        postDelayed(timerTask, duration);
    }

    /**
     * 关闭定时器任务
     */
    private void stopLopperTask() {
        if (timerTask != null) timerTask.cancel();
        if (timer != null) timer.cancel();
    }
}
