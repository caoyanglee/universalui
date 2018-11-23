package com.weimu.universalview.widget.swipelist;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 可滑动布局
 */
public class SwipeItemLayout extends FrameLayout {


    private View menu;//菜单  视图
    private View content;//内容   视图

    private boolean canSwip = true;

    private final ViewDragHelper dragHelper;
    private boolean isOpen;//是否打开
    private int currentState;//当前状态


    public SwipeItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, rightCallback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menu = getChildAt(0);
        content = getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    private ViewDragHelper.Callback rightCallback = new ViewDragHelper.Callback() {

        // 触摸到View的时候就会回调这个方法。
        // return true表示抓取这个View。
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return content == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (!canSwip) {
                return 0;
            }
            return left > 0 ? 0 : left < -menu.getWidth() ? -menu.getWidth() : left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            // x轴移动速度大于菜单一半，或者已经移动到菜单的一般之后，展开菜单

            //Logger.e("ViewDragHelper  xvel=" + xvel + "  yvel=" + yvel);
            if (isOpen && canSwip) {
                if (xvel > menu.getWidth() || -content.getLeft() < menu.getWidth() / 2) {
                    close();
                } else {
                    open();
                }
            } else {
                if (-xvel > menu.getWidth() || -content.getLeft() > menu.getWidth() / 2) {
                    open();
                } else {
                    close();
                }
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 1;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return 1;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            currentState = state;
        }
    };

    public void close() {
        dragHelper.smoothSlideViewTo(content, 0, 0);
        isOpen = false;
        invalidate();
    }

    public void open() {
        dragHelper.smoothSlideViewTo(content, -menu.getWidth(), 0);
        isOpen = true;
        invalidate();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getState() {
        return currentState;
    }

    private Rect outRect = new Rect();

    public Rect getMenuRect() {
        menu.getHitRect(outRect);
        return outRect;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        content.setOnClickListener(l);
    }

    public void setSwipeAble(boolean canSwip) {
        this.canSwip = canSwip;
    }


}
