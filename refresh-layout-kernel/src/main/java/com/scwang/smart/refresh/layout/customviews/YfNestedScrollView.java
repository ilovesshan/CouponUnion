package com.scwang.smart.refresh.layout.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/24
 * @description:
 */
public class YfNestedScrollView extends NestedScrollView {

    private static final String TAG = "YfNestedScrollView";

    /**
     * 当前view滚动的距离
     */
    private int currentScroll = 0;

    /**
     * 需要被消费的高度(轮播图 + 标题栏高度)
     */
    private int needConsumeHeight = 0;
    private RecyclerView targetView;


    public YfNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public YfNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public YfNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 设置当前需要被消费的高度
     *
     * @param needConsumeHeight header的高度
     */
    public void setNeedConsumeHeight(int needConsumeHeight) {
        this.needConsumeHeight = needConsumeHeight;
    }

    /**
     * React to a nested scroll in progress before the target view consumes a portion of the scroll.
     *
     * <p>When working with nested scrolling often the parent view may want an opportunity
     * to consume the scroll before the nested scrolling child does. An example of this is a
     * drawer that contains a scrollable list. The user will want to be able to scroll the list
     * fully into view before the list itself begins scrolling.</p>
     *
     * <p><code>onNestedPreScroll</code> is called when a nested scrolling child invokes
     * {@link View#dispatchNestedPreScroll(int, int, int[], int[])}. The implementation should
     * report how any pixels of the scroll reported by dx, dy were consumed in the
     * <code>consumed</code> array. Index 0 corresponds to dx and index 1 corresponds to dy.
     * This parameter will never be null. Initial values for consumed[0] and consumed[1]
     * will always be 0.</p>
     *
     * @param target   View that initiated the nested scroll
     * @param dx       Horizontal scroll distance in pixels
     * @param dy       Vertical scroll distance in pixels
     * @param consumed Output. The horizontal and vertical scroll distance consumed by this parent
     * @param type     the type of input which cause this scroll event
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (target instanceof RecyclerView) {
            this.targetView = (RecyclerView) target;
        }

        super.onNestedPreScroll(target, dx, dy, consumed, type);
        // 当 当前滚动距离超过 需要被消费的高度(轮播图 + 标题栏高度)高度时，父容器不再消费滚动事件，而把这个事件交给子控件去消费
        // if (dy < 0 || currentScroll < needConsumeHeight) {
        if (currentScroll < needConsumeHeight) {
            scrollBy(dx, dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
    }


    /**
     * This is called in response to an internal scroll in this view (i.e., the
     * view scrolled its own contents). This is typically as a result of
     * {@link #scrollBy(int, int)} or {@link #scrollTo(int, int)} having been
     * called.
     *
     * @param l    Current horizontal scroll origin.
     * @param t    Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        this.currentScroll = t;
    }

    public boolean isArriveButton() {
        if (targetView != null) {
            final boolean isArriveButton = !targetView.canScrollVertically(1);
            return isArriveButton;
        }
        return false;
    }
}
