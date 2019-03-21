package com.weimu.app.universalview.module.customview.vml;

import android.graphics.Rect;
import android.view.View;

import static android.view.View.MeasureSpec;

public class GridLayoutManager extends ConfigurableViewGroup.LayoutManager {
    private int spanCount;
    private int orientation;
    private SpanSizeLookup spanSizeLookup;


    public GridLayoutManager(int spanCount) {
        this(spanCount, ConfigurableViewGroup.VERTICAL);
    }

    private GridLayoutManager(int spanCount, @ConfigurableViewGroup.Orientation int orientation) {
        this.spanCount = spanCount;
        this.orientation = orientation;

        spanSizeLookup = new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        };

    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        if (spanSizeLookup != null) {
            this.spanSizeLookup = spanSizeLookup;
            requestLayout();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (orientation == ConfigurableViewGroup.VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (orientation == ConfigurableViewGroup.VERTICAL) {
            layoutVertical();
        } else {

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

    private void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(getZeroMeasuredDimension(widthMeasureSpec),
                    getZeroMeasuredDimension(heightMeasureSpec));
        } else {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            if (widthMode == MeasureSpec.EXACTLY) {
                final int parentWidthSize = MeasureSpec.getSize(widthMeasureSpec);
                final int itemWidth = parentWidthSize / spanCount;
                int remainSpanCount = spanCount;
                int currentColumnCount = 0;
                int startIndex = 0;
                int totalHeight = 0;
                boolean nextRow;
                for (int index = 0; index < childCount; index++) {
                    int currentSpanCount = spanSizeLookup.getSpanSize(index);
                    if (currentSpanCount > spanCount) {
                        throw new IllegalStateException("child: " + index + " spanSize>maxSpanSize");
                    }

                    if (remainSpanCount == spanCount) {
                        startIndex = index;
                        currentColumnCount = 0;
                    }
                    if (remainSpanCount >= currentSpanCount) {
                        Rect rect = getDecorationRect(index);
                        remainSpanCount -= currentSpanCount;
                        currentColumnCount++;
                    }
                    if (index < childCount - 1) {
                        nextRow = (remainSpanCount < spanSizeLookup.getSpanSize(index + 1));
                    } else {
                        nextRow = (index == childCount - 1);
                    }

                    if (nextRow) {

                        int maxHeight = 0;
                        for (int childIndex = startIndex; childIndex < startIndex + currentColumnCount; childIndex++) {
                            View currentColumnView = getChildAt(childIndex);
                            Rect currentColumnRect = getDecorationRect(childIndex);
                            int currentItemWidth = itemWidth * spanSizeLookup.getSpanSize(childIndex) - currentColumnRect.left - currentColumnRect.right;
                            if (currentItemWidth < 0) {
                                currentItemWidth = 0;
                            }
                            measureChild(currentColumnView, MeasureSpec.makeMeasureSpec(
                                    currentItemWidth, MeasureSpec.EXACTLY),
                                    heightMeasureSpec);
                            maxHeight = Math.max(maxHeight, currentColumnView.getMeasuredHeight() + currentColumnRect.top + currentColumnRect.bottom);

                        }
                        totalHeight += maxHeight;

                        remainSpanCount = spanCount;
                    }


                }
                setMeasuredDimension(parentWidthSize, totalHeight);
            } else {
                int consumerSpanCount = spanCount;
                int currentColumnCount = 0;
                int startIndex = 0;
                int totalHeight = 0;
                int totalWidth = 0;
                boolean nextRow;
                for (int index = 0; index < childCount; index++) {
                    int currentSpanCount = spanSizeLookup.getSpanSize(index);
                    if (currentSpanCount > spanCount) {
                        throw new IllegalStateException("child: " + index + " spanSize>maxSpanSize");
                    }

                    if (consumerSpanCount == spanCount) {
                        startIndex = index;
                        currentColumnCount = 0;
                    }
                    if (consumerSpanCount >= currentSpanCount) {
                        consumerSpanCount -= currentSpanCount;
                        currentColumnCount++;
                    }
                    if (index < childCount - 1) {
                        nextRow = (consumerSpanCount < spanSizeLookup.getSpanSize(index + 1));
                    } else {
                        nextRow = (index == childCount - 1);
                    }

                    if (nextRow) {
                        int currentWidth = 0;
                        int maxHeight = 0;
                        for (int childIndex = startIndex; childIndex < startIndex + currentColumnCount; childIndex++) {
                            View currentColumnView = getChildAt(childIndex);

                            Rect currentColumnRect = getDecorationRect(childIndex);
                            measureChild(currentColumnView, widthMeasureSpec, heightMeasureSpec);
                            maxHeight = Math.max(maxHeight, currentColumnView.getMeasuredHeight() + currentColumnRect.top + currentColumnRect.bottom);
                            currentWidth += currentColumnView.getMeasuredWidth() + currentColumnRect.left + currentColumnRect.right;

                        }
                        totalHeight += maxHeight;
                        totalWidth = Math.max(currentWidth, totalWidth);
                        consumerSpanCount = spanCount;
                    }


                }
                setMeasuredDimension(totalWidth, totalHeight);
            }
        }

    }

    private void layoutVertical() {
        final int childCount = getChildCount();
        if (childCount != 0) {
            int consumerSpanCount = spanCount;
            int currentColumnCount = 0;
            int startIndex = 0;
            int currentHeight = 0;
            boolean nextRow;

            for (int index = 0; index < childCount; index++) {
                int currentSpanCount = spanSizeLookup.getSpanSize(index);
                if (consumerSpanCount == spanCount) {
                    startIndex = index;
                    currentColumnCount = 0;
                }
                if (consumerSpanCount >= currentSpanCount) {
                    consumerSpanCount -= currentSpanCount;
                    currentColumnCount++;
                }

                if (index < childCount - 1) {
                    nextRow = (consumerSpanCount < spanSizeLookup.getSpanSize(index + 1));
                } else {
                    nextRow = (index == childCount - 1);
                }

                if (nextRow) {
                    int maxHeight = 0;
                    int childLeft = 0;
                    for (int childIndex = startIndex; childIndex < startIndex + currentColumnCount; childIndex++) {
                        View currentColumnView = getChildAt(childIndex);
                        Rect currentColumnRect = getDecorationRect(childIndex);
                        maxHeight = Math.max(maxHeight, currentColumnView.getMeasuredHeight() + currentColumnRect.top + currentColumnRect.bottom);

                        if (childIndex == startIndex) {
                            childLeft = currentColumnRect.left;
                        } else {
                            childLeft += currentColumnRect.left;
                        }
                        currentColumnView.layout(childLeft, currentHeight + currentColumnRect.top,
                                childLeft + currentColumnView.getMeasuredWidth(), currentHeight + currentColumnRect.top + currentColumnView.getMeasuredHeight());
                        childLeft += (currentColumnView.getMeasuredWidth() + currentColumnRect.right);
                    }
                    currentHeight += maxHeight;
                    consumerSpanCount = spanCount;
                }

            }
        }

    }


    public abstract static class SpanSizeLookup {
        public abstract int getSpanSize(int position);
    }


}
