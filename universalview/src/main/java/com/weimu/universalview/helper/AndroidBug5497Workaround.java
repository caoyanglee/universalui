package com.weimu.universalview.helper;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;



//防止键盘挡住编辑内容

public class AndroidBug5497Workaround {
    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity(Activity activity, boolean isFullScreen) {
        new AndroidBug5497Workaround(activity, isFullScreen);
    }

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity, false);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(final Activity activity, final boolean isFullScreen) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent(isFullScreen);
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent(boolean isFullScreen) {
        int usableHeightNow = computeUsableHeight(isFullScreen);
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightNow - usableHeightNow;

            //Logger.e("usableHeightSansKeyboard=" + usableHeightSansKeyboard + " usableHeightNow=" + usableHeightNow + " heightDifference=" + heightDifference);

            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightNow - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightNow;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight(boolean isFullScreen) {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        if (isFullScreen) {
            return r.bottom;// 全屏模式下： return r.bottom
        }
        return (r.bottom - r.top);//非全屏模式
    }


}

