package com.weimu.app.universalview.module.customview.vml;

import android.graphics.Rect;
import android.view.View;

import static android.view.View.MeasureSpec;

public class FlowLayoutManager extends ConfigurableViewGroup.LayoutManager {
    private int orientation;

    public FlowLayoutManager() {
        this.orientation = ConfigurableViewGroup.VERTICAL;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(getZeroMeasuredDimension(widthMeasureSpec),
                    getZeroMeasuredDimension(heightMeasureSpec));
        } else {
            for (int index = 0; index < childCount; index++) {
                measureChild(getChildAt(index), widthMeasureSpec, heightMeasureSpec);
            }

            final int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
            int totalHeight = 0;
            int maxHeight = 0;
            int remainWidth = parentWidthSize;
            for (int index = 0; index < childCount; index++) {
                View child = getChildAt(index);
                Rect rect = getDecorationRect(index);

                if (child.getMeasuredWidth() >= parentWidthSize - rect.left - rect.right) {
                    totalHeight += (child.getMeasuredHeight() + rect.top + rect.bottom);
                    child.measure(MeasureSpec.makeMeasureSpec(parentWidthSize - rect.left - rect.top, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                    maxHeight = 0;
                    remainWidth = parentWidthSize;
                } else {
                    if (remainWidth >= child.getMeasuredWidth() + rect.left + rect.right) {
                        maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + rect.top + rect.bottom);
                        remainWidth -= (child.getMeasuredWidth() + rect.left + rect.right);
                    } else {
                        totalHeight += maxHeight;
                        maxHeight = 0;
                        remainWidth = parentWidthSize;
                        index -= 1;
                    }
                }
                if (index == childCount - 1) {
                    totalHeight += maxHeight;
                }

            }
            setMeasuredDimension(parentWidthSize, totalHeight);

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        if (childCount != 0) {
            final int parentWidth = getParentMeasuredWidth();
            int remainWidth = parentWidth;
            int currentHeight = 0;
            int maxHeight = 0;
            int childLeft = 0;
            for (int index = 0; index < childCount; index++) {
                View child = getChildAt(index);
                Rect rect = getDecorationRect(index);
                if (child.getMeasuredWidth() == parentWidth - rect.left - rect.right) {
                    child.layout(rect.left,
                            currentHeight + rect.top,
                            rect.left + child.getMeasuredWidth(),
                            currentHeight + rect.top + child.getMeasuredHeight());
                    currentHeight += (child.getMeasuredHeight() + rect.top + rect.bottom);
                    maxHeight = 0;
                    childLeft = 0;
                } else {
                    if (remainWidth >= child.getMeasuredWidth() + rect.left + rect.right) {
                        maxHeight = Math.max(maxHeight, child.getMeasuredHeight() + rect.top + rect.bottom);
                        remainWidth -= (child.getMeasuredWidth() + rect.left + rect.right);
                        if (childLeft == 0) {
                            childLeft = rect.left;
                        }
                        child.layout(childLeft, currentHeight + rect.top,
                                childLeft + child.getMeasuredWidth(),
                                currentHeight + rect.top + child.getMeasuredHeight());
                        childLeft += (rect.right + child.getMeasuredWidth());
                    } else {
                        currentHeight += maxHeight;
                        childLeft = 0;
                        maxHeight = 0;
                        remainWidth = parentWidth;
                        index -= 1;
                    }
                }
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
}
