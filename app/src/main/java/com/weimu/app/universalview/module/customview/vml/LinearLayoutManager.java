package com.weimu.app.universalview.module.customview.vml;

import android.graphics.Rect;
import android.view.View;

import static android.view.View.MeasureSpec;

public class LinearLayoutManager extends ConfigurableViewGroup.LayoutManager {
    private int orientation;

    public LinearLayoutManager() {
        this(ConfigurableViewGroup.VERTICAL);
    }

    private LinearLayoutManager(@ConfigurableViewGroup.Orientation int orientation) {
        this.orientation = orientation;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (orientation == ConfigurableViewGroup.VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            // measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (orientation == ConfigurableViewGroup.VERTICAL) {
            layoutVertical();
        } else {
            // layoutHorizontal();
        }
    }


    private void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(getZeroMeasuredDimension(widthMeasureSpec),
                    getZeroMeasuredDimension(heightMeasureSpec));
        } else {
            final int parentWidthMode = MeasureSpec.getMode(widthMeasureSpec);
            int totalHeight = 0;
            int maxWidth = 0;
            final int parentWidth = MeasureSpec.getSize(widthMeasureSpec);

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                Rect rect = getDecorationRect(i);
                int currentItemWidth = 0;
                if (parentWidthMode == MeasureSpec.EXACTLY || parentWidthMode == MeasureSpec.AT_MOST) {
                    currentItemWidth = Math.min(child.getMeasuredWidth(), parentWidth) - rect.left - rect.right;
                } else if (parentWidthMode == MeasureSpec.UNSPECIFIED) {
                    currentItemWidth = child.getMeasuredWidth();
                }

                if (currentItemWidth < 0) {
                    currentItemWidth = 0;
                }
                child.measure(MeasureSpec.makeMeasureSpec(currentItemWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                totalHeight += child.getMeasuredHeight() + rect.top + rect.bottom;
                maxWidth = Math.max(maxWidth, child.getMeasuredWidth() + rect.left + rect.right);
            }

            switch (parentWidthMode) {
                case MeasureSpec.EXACTLY:
                    setMeasuredDimension(parentWidth, totalHeight);
                    break;
                case MeasureSpec.AT_MOST:
                case MeasureSpec.UNSPECIFIED:
                    setMeasuredDimension(maxWidth, totalHeight);
                    break;
            }

        }
    }

    private void layoutVertical() {
        final int childCount = getChildCount();
        if (childCount != 0) {
            int totalHeight = 0;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                Rect rect = getDecorationRect(i);
                totalHeight += rect.top;
                child.layout(rect.left, totalHeight, rect.left + child.getMeasuredWidth(),
                        totalHeight + child.getMeasuredHeight());
                totalHeight += child.getMeasuredHeight() + rect.bottom;
            }
        }
    }

    private int getZeroMeasuredDimension(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                return specSize;
            case MeasureSpec.UNSPECIFIED:
                return 0;
            case MeasureSpec.AT_MOST:
                return 0;
            default:
                return 0;
        }

    }

//    private void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
//
//        final int childCount = getChildCount();
//        if (childCount == 0) {
//            setMeasuredDimension(getZeroMeasuredDimension(widthMeasureSpec),
//                    getZeroMeasuredDimension(widthMeasureSpec));
//        } else {
//            int totalWidth = 0;
//            for (int i = 0; i < childCount; i++) {
//                View child = getChildAt(i);
//                measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
//                Rect rect = getDecorationRect(i);
//                child.measure(MeasureSpec.makeMeasureSpec(child.getMeasuredWidth() - rect.left - rect.right, MeasureSpec.EXACTLY),
//                        MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
//                totalWidth += child.getMeasuredWidth() + rect.left + rect.right;
//            }
//            setMeasuredDimension(totalWidth, heightMeasureSpec);
//        }
//    }
//
//    private void layoutHorizontal() {
//
//    }
}
