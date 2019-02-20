package com.weimu.app.universalview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

import com.weimu.app.universalview.R;

public class GridInputText extends AppCompatEditText {
    private static final String TAG = "GridInputView";
    private final int[] mFocusState = new int[]{android.R.attr.state_focused};
    private final int[] mNormalState = new int[]{android.R.attr.state_empty};

    private int mMaxInputLength = 6;
    private Drawable mNormalDrawable;
    private Drawable mFocusDrawable;
    private Drawable sourceDrawable;
    private boolean mIsInputHide = false;
    private int mChildMargin = 0;
    private int mInputType = INPUT_NUMBER;
    private int itemBackgroundType;
    private int mLineWidth = 1;
    private int mLineColor;
    private boolean cursorVisible = true;
    //文本
    public static final int INPUT_TEXT = 0;
    //数字
    public static final int INPUT_NUMBER = 1;
    //Item是线条
    public static final int ITEM_BG_TYPE_LINE = 1;
    //Item是矩形
    public static final int ITEM_BG_TYPE_RECT = 0;
    //Item自定义
    public static final int ITEM_BG_TYPE_SELF = 2;

    private int mCurrentPasswordLength = 0;
    private Paint mTextPaint = new Paint();
    private Paint mRadiusPaint = new Paint();
    private Paint mCursorPaint = new Paint();
    private CharSequence mText = "";
    private Rect maxRect = new Rect();
    private Rect mRect = new Rect();
    private int inputViewSquareSize = 0;
    private boolean isDrawCursor = false;
    private final int mCursorWidth;
    private Runnable drawCursorRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCurrentPasswordLength >= mMaxInputLength) {
                isDrawCursor = false;
            } else {
                if (hasFocus()) {
                    isDrawCursor = !isDrawCursor;
                } else {
                    isDrawCursor = false;
                }

            }

            postInvalidate();
            removeCallbacks(drawCursorRunnable);
            postDelayed(drawCursorRunnable, 1000);
        }
    };

    public GridInputText(Context context) {
        this(context, null);
    }

    public GridInputText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridInputText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridInputText);
        setMaxInputLength(a.getInt(R.styleable.GridInputText_gridInputLength, 6));
        setItemBackgroundType(a.getInt(R.styleable.GridInputText_gridInputItemBackgroundType, ITEM_BG_TYPE_RECT));
        Drawable drawable = a.getDrawable(R.styleable.GridInputText_gridInputItemBackground);
        if (drawable != null) {
            setInputBackgroundDrawable(drawable);
        }
        setInputHide(a.getBoolean(R.styleable.GridInputText_gridInputHide, true));
        setGridInputType(a.getInt(R.styleable.GridInputText_gridInputType, INPUT_NUMBER));
        setTextColor(a.getColor(R.styleable.GridInputText_gridInputTextColor, getCurrentTextColor()));
        setTextSize(a.getDimension(R.styleable.GridInputText_gridInputTextSize, getTextSize()));
        setChildMargin(a.getDimensionPixelOffset(R.styleable.GridInputText_gridInputChildMargin, 0));
        setItemBackgroundLineWidth(a.getDimensionPixelOffset(R.styleable.GridInputText_gridInputItemLineBackgroundWidth, 0));
        setItemBackgroundLineColor(a.getColor(R.styleable.GridInputText_gridInputTextColor, getCurrentTextColor()));
        a.recycle();
        mRadiusPaint.setAntiAlias(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mCursorPaint.setAntiAlias(true);
        mCursorPaint.setColor(getCurrentTextColor());
        mCursorWidth = px(1);
        super.setCursorVisible(false);
        super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setCursorVisible(true);
    }


    @Override
    public void setCursorVisible(boolean cursorVisible) {
        this.cursorVisible = cursorVisible;
        if (cursorVisible) {
            showCursorVisible(true);
        } else {
            showCursorVisible(false);
        }
    }

    private void showCursorVisible(boolean isShow) {
        if (isShow && cursorVisible) {
            isDrawCursor = false;
            postDelayed(drawCursorRunnable, 1000);
        } else {
            removeCallbacks(drawCursorRunnable);
            isDrawCursor = false;
        }
    }

    @Override
    public void setTextColor(int color) {
        mTextPaint.setColor(color);
        mRadiusPaint.setColor(color);
        super.setTextColor(color);
    }

    @Override
    public void setTextSize(float size) {
        mTextPaint.setTextSize(size);
        mCursorPaint.setTextSize(size);
        super.setTextSize(size);
    }

    /**
     * 设置输出类型
     *
     * @param inputType 文本/数字
     */
    public void setGridInputType(int inputType) {
        mInputType = inputType;
        if (mInputType == INPUT_NUMBER) {
            setInputType(android.text.InputType.TYPE_CLASS_NUMBER
                    | android.text.InputType.TYPE_NUMBER_FLAG_SIGNED);
        } else {
            setInputType(android.text.InputType.TYPE_CLASS_TEXT);
        }
    }

    /**
     * 是否隐藏输入
     *
     * @param isInputHide
     */
    public void setInputHide(boolean isInputHide) {
        if (isInputHide == mIsInputHide) {
            return;
        }
        this.mIsInputHide = isInputHide;
        invalidate();
    }

    /**
     * 设置输入背景图
     *
     * @param drawable
     */
    public void setInputBackgroundDrawable(Drawable drawable) {
        if (drawable == null) {
            mNormalDrawable = null;
            mFocusDrawable = null;
        } else {
            drawable.setState(mNormalState);
            mNormalDrawable = drawable.getCurrent();
            drawable.setState(mFocusState);
            mFocusDrawable = drawable.getCurrent();
        }
        this.itemBackgroundType = ITEM_BG_TYPE_SELF;
        this.sourceDrawable = drawable;
        invalidate();
    }

    public void setItemBackgroundType(int itemBackgroundType) {
        if (itemBackgroundType == ITEM_BG_TYPE_RECT) {
            setInputBackgroundDrawable(getItemRectDrawable());
        } else if (itemBackgroundType == ITEM_BG_TYPE_LINE) {
            setInputBackgroundDrawable(getItemLineDrawable());
        } else {
            setInputBackgroundDrawable(null);
        }
        this.itemBackgroundType = itemBackgroundType;
    }

    public void setItemBackgroundLineWidth(int mLineWidth) {
        if (itemBackgroundType == ITEM_BG_TYPE_LINE &&
                sourceDrawable instanceof GradientDrawable) {
            this.mLineWidth = mLineWidth;
            ((GradientDrawable) sourceDrawable).setStroke(mLineWidth, mLineColor);
            invalidate();
        }

    }

    public void setItemBackgroundLineColor(int color) {
        if (itemBackgroundType == ITEM_BG_TYPE_LINE &&
                sourceDrawable instanceof GradientDrawable) {
            mLineColor = color;
            ((GradientDrawable) sourceDrawable).setStroke(mLineWidth, mLineColor);
            invalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        showCursorVisible(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showCursorVisible(true);
    }

    /**
     * 设置最大输入长度
     */
    public void setMaxInputLength(int maxLength) {
        if (maxLength < 0) {
            return;
        }
        mMaxInputLength = maxLength;
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxInputLength)});
        invalidate();
    }

    public void setChildMargin(int childMargin) {
        if (mChildMargin == childMargin) {
            return;
        }
        this.mChildMargin = childMargin;
        invalidate();
    }

    private int px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        mCurrentPasswordLength = text.length();
        mText = text;
        invalidate();
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        Editable editable = getText();
        if (editable != null) {
            int length = editable.length();
            if (selStart != length || selEnd != length) {
                setSelection(length);
            }
        }

    }

    protected Drawable getItemRectDrawable() {
        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setStroke(2, 0xFFE0E0E0);
        defaultDrawable.setColor(0xFFFFFFFF);
        return defaultDrawable;
    }

    private Drawable getItemLineDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.LINE);
        drawable.setColor(getCurrentTextColor());
        return drawable;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            showCursorVisible(true);
        } else {
            showCursorVisible(false);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mNormalDrawable == null) {
            setMeasuredDimension(0, 0);
        } else {
            if (mMaxInputLength <= 0) {
                setMeasuredDimension(0, 0);
            } else {
                final int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
                inputViewSquareSize = (parentWidth - mChildMargin * (mMaxInputLength + 1)) / mMaxInputLength;
                if (inputViewSquareSize <= 0) {
                    setMeasuredDimension(0, 0);
                } else {
                    switch (MeasureSpec.getMode(heightMeasureSpec)) {
                        case MeasureSpec.EXACTLY:
                            setMeasuredDimension(parentWidth, MeasureSpec.getSize(heightMeasureSpec));
                            break;
                        case MeasureSpec.AT_MOST:
                        case MeasureSpec.UNSPECIFIED:
                        default:
                            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), inputViewSquareSize);
                            break;

                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mNormalDrawable == null) {
            return;
        }
        if (mMaxInputLength == 0) {
            return;
        }


        int maxRectLeft = mChildMargin;
        final int widgetHeight = getMeasuredHeight();
        for (int index = 0; index < mMaxInputLength; index++) {
            maxRect.set(maxRectLeft, 0,
                    maxRectLeft + inputViewSquareSize, widgetHeight);
            mRect.set(maxRect.centerX() - inputViewSquareSize / 2,
                    maxRect.centerY() - widgetHeight / 2,
                    maxRect.centerX() + inputViewSquareSize / 2,
                    maxRect.centerY() + widgetHeight / 2);
            if (itemBackgroundType == ITEM_BG_TYPE_LINE) {
                //line的特性，它会显示在bounds的中央，为了移动到下班，超边界
                mNormalDrawable.setBounds(mRect.left,
                        mRect.top + mRect.centerY() - mLineWidth,
                        mRect.right,
                        mRect.bottom + mRect.centerY());
            } else {
                mNormalDrawable.setBounds(mRect.left,
                        mRect.top,
                        mRect.right,
                        mRect.bottom);
            }
            mNormalDrawable.draw(canvas);
            if (mCurrentPasswordLength > index) {
                if (mIsInputHide) {
                    canvas.drawCircle(mRect.centerX(), mRect.centerY(), ((mTextPaint.descent() - mTextPaint.ascent()) / 2), mRadiusPaint);
                } else {
                    Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
                    float top = fontMetrics.top;
                    float bottom = fontMetrics.bottom;
                    canvas.drawText(mText.subSequence(index, index + 1) + "", mRect.centerX(), (int) (mRect.centerY() - top / 2 - bottom / 2), mTextPaint);
                }
            }
            maxRectLeft += inputViewSquareSize + mChildMargin;
        }
        int a = 1;
        if (isDrawCursor && mCurrentPasswordLength < mMaxInputLength) {
            float left = (inputViewSquareSize + mChildMargin) * mCurrentPasswordLength + mChildMargin +
                    inputViewSquareSize / 2 - mCursorWidth * 1.0f / 2;
            float top = widgetHeight / 2.0f - (mTextPaint.descent() - mTextPaint.ascent()) / 2.0f;
            canvas.drawRect(left, top, left + mCursorWidth * 1.0f, top + mTextPaint.descent() - mTextPaint.ascent(), mCursorPaint);
        }


    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return new ArrowKeyMovementMethod() {
            @Override
            protected boolean handleMovementKey(TextView widget, Spannable buffer, int keyCode, int movementMetaState, KeyEvent event) {
                return super.handleMovementKey(widget,buffer,keyCode,movementMetaState,event);
            }

            @Override
            protected boolean left(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean right(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean up(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean down(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean pageUp(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean pageDown(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean top(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean bottom(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean lineStart(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean lineEnd(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean home(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            protected boolean end(TextView widget, Spannable buffer) {
                return false;
            }

            @Override
            public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
                return false;
            }

            @Override
            public boolean canSelectArbitrarily() {
                return false;
            }

            @Override
            public void initialize(TextView widget, Spannable text) {
                setSelection(text.length());
            }

            @Override
            public void onTakeFocus(TextView view, Spannable text, int dir) {

            }
        };
    }
}
