package com.weimu.app.universalview.module.customview.vml;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;

public class ConfigurableViewGroup extends ViewGroup {

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation { }

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private LayoutManager mLayoutManager;
    private Rect decorationRect = new Rect();
    private ItemDecoration itemDecoration;

    public ConfigurableViewGroup(Context context) {
        this(context, null);
    }

    public ConfigurableViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConfigurableViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setItemDecoration(ItemDecoration itemDecoration) {
        if (this.itemDecoration == null) {
            setWillNotDraw(false);
        }
        this.itemDecoration = itemDecoration;
        requestLayout();
    }

    public void setLayoutManager(@NonNull LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        mLayoutManager.mParent = this;
        requestLayout();
    }

    public LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mLayoutManager != null) {
            mLayoutManager.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mLayoutManager != null) {
            mLayoutManager.onLayout(changed, l, t, r, b);
        }
    }

    private Rect getDecorationRect(int position) {
        if (itemDecoration != null) {
            decorationRect.bottom = 0;
            decorationRect.left = 0;
            decorationRect.top = 0;
            decorationRect.right = 0;
            itemDecoration.getItemOffsets(decorationRect, position, this);
            return decorationRect;
        } else {
            decorationRect.bottom = 0;
            decorationRect.left = 0;
            decorationRect.top = 0;
            decorationRect.right = 0;
            return decorationRect;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) p);
        } else {
            return new LayoutParams(p);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (itemDecoration != null) {
            itemDecoration.onDraw(canvas, this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (itemDecoration != null) {
            itemDecoration.onDrawOver(canvas, this);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p) && p instanceof LayoutParams;
    }


    public static class ItemDecoration {

        public void onDrawOver(Canvas c, ConfigurableViewGroup parent) {

        }

        public void getItemOffsets(Rect outRect, int itemPosition, ConfigurableViewGroup parent) {
            outRect.set(0, 0, 0, 0);
        }

        public void onDraw(Canvas c, ConfigurableViewGroup parent) {

        }
    }


    public static class LayoutParams extends MarginLayoutParams {


        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }


    public abstract static class LayoutManager {
        ConfigurableViewGroup mParent;

        protected abstract void onMeasure(int widthMeasureSpec, int heightMeasureSpec);

        protected abstract void onLayout(boolean changed, int l, int t, int r, int b);

        public int getChildCount() {
            return mParent != null ? mParent.getChildCount() : 0;

        }

        public View getChildAt(int index) {
            return mParent != null ? mParent.getChildAt(index) : null;

        }

        protected final void setMeasuredDimension(int measuredWidth, int measuredHeight) {
            if (mParent != null) {
                mParent.setMeasuredDimension(measuredWidth, measuredHeight);
            }

        }

        protected void measureChild(View child, int parentWidthMeasureSpec,
                                    int parentHeightMeasureSpec) {
            if (mParent != null) {
                mParent.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
            }

        }

        protected int getSuggestedMinimumHeight() {
            return mParent != null ? mParent.getSuggestedMinimumHeight() : 0;
        }


        public int getSuggestedMinimumWidth() {
            return mParent != null ? mParent.getSuggestedMinimumWidth() : 0;
        }

        public void requestLayout() {
            if (mParent != null) {
                mParent.requestLayout();
            }

        }

        protected Rect getDecorationRect(int position) {
            return mParent != null ? mParent.getDecorationRect(position) : new Rect();
        }

        protected int getParentMeasuredHeight() {
            return mParent != null ? mParent.getMeasuredHeight() : 0;
        }

        protected int getParentMeasuredWidth() {
            return mParent != null ? mParent.getMeasuredWidth() : 0;
        }

        protected ViewGroup.LayoutParams getLayoutParams() {
            return mParent != null ? mParent.getLayoutParams() : null;
        }
    }
}
