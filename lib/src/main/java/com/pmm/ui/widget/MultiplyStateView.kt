package com.pmm.ui.widget

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import com.pmm.ui.R

/**
 * @author 微木森林
 * @date 2017/6/6 下午4:17
 * @description 多视图状态
 */
open class MultiplyStateView : FrameLayout {

    companion object {
        val VIEW_STATE_CONTENT = 0
        val VIEW_STATE_ERROR = 1
        val VIEW_STATE_EMPTY = 2
        val VIEW_STATE_LOADING = 3
    }


    lateinit var mInflater: LayoutInflater

    var mContentView: View? = null
    var mLoadingView: View? = null
    var mErrorView: View? = null
    var mEmptyView: View? = null


    var mViewState: Int = VIEW_STATE_CONTENT


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs)
    }


    fun init(attrs: AttributeSet?) {
        mInflater = LayoutInflater.from(context)
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultiStateViewWM)

        //inflate loading view
        val loadingViewResId = a.getResourceId(R.styleable.MultiStateViewWM_msv_loadingView, -1)
        if (loadingViewResId > -1) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false)
            addView(mLoadingView, mLoadingView?.layoutParams)
        }

        //inflate empty view
        val emptyViewResId = a.getResourceId(R.styleable.MultiStateViewWM_msv_emptyView, -1)
        if (emptyViewResId > -1) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false)
            addView(mEmptyView, mEmptyView?.layoutParams)
        }

        //inflate error view
        val errorViewResId = a.getResourceId(R.styleable.MultiStateViewWM_msv_errorView, -1)
        if (errorViewResId > -1) {
            mErrorView = mInflater.inflate(errorViewResId, this, false)
            addView(mErrorView, mErrorView?.layoutParams)
        }

        //current view state
        var viewState = a.getInt(R.styleable.MultiStateViewWM_msv_viewState, VIEW_STATE_CONTENT)

        //switch view
        when (viewState) {
            VIEW_STATE_CONTENT -> mViewState = VIEW_STATE_CONTENT
            VIEW_STATE_ERROR -> mViewState = VIEW_STATE_ERROR
            VIEW_STATE_EMPTY -> mViewState = VIEW_STATE_EMPTY
            VIEW_STATE_LOADING -> mViewState = VIEW_STATE_LOADING
        }
        //recycle for preventing app from ram leaking
        a.recycle()


        this.layoutTransition = LayoutTransition()//设置布局动画
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mContentView == null) throw IllegalArgumentException("Content view is not defined")
        notifyView()
    }

    /* All of the addView methods have been overridden so that it can obtain the content view via XML
    It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
    any of the setViewForState methods to set views for their given ViewState accordingly */

    override fun addView(child: View?) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child)
    }

    override fun addView(child: View?, index: Int) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index)
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, params)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, index, params)
    }

    override fun addView(child: View?, width: Int, height: Int) {
        if (isValidContentView(child)) mContentView = child;
        super.addView(child, width, height)
    }


    override fun addViewInLayout(child: View?, index: Int, params: ViewGroup.LayoutParams?): Boolean {
        if (isValidContentView(child)) mContentView = child
        return super.addViewInLayout(child, index, params)
    }


    override fun addViewInLayout(child: View?, index: Int, params: ViewGroup.LayoutParams?, preventRequestLayout: Boolean): Boolean {
        if (isValidContentView(child)) mContentView = child
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    @Nullable
    fun getView(state: Int): View? {
        when (state) {
            VIEW_STATE_LOADING -> return mLoadingView
            VIEW_STATE_CONTENT -> return mContentView
            VIEW_STATE_EMPTY -> return mEmptyView
            VIEW_STATE_ERROR -> return mErrorView
            else -> return null
        }
    }

    fun getViewState(): Int {
        return mViewState
    }

    fun setViewState(state: Int) {
        if (state != mViewState) {
            mViewState = state
            notifyView()
        }
    }


    /**
     * Checks if the given {@link View} is valid for the Content View
     * @param view The {@link View} to check
     * @return
     */
    fun isValidContentView(view: View?): Boolean {
        if (mContentView != null && mContentView != view) {
            return false
        }
        return view != mLoadingView && view != mErrorView && view != mEmptyView;
    }


    fun notifyView() {
        when (mViewState) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView == null) throw NullPointerException("Loading View")

                mLoadingView?.visibility = View.VISIBLE
                if (mContentView != null) mContentView?.visibility = View.GONE
                if (mErrorView != null) mErrorView?.visibility = View.GONE
                if (mEmptyView != null) mEmptyView?.visibility = View.GONE
            }

            VIEW_STATE_EMPTY -> {
                if (mEmptyView == null) throw NullPointerException("Empty View")

                mEmptyView?.visibility = View.VISIBLE
                if (mLoadingView != null) mLoadingView?.visibility = View.GONE
                if (mErrorView != null) mErrorView?.visibility = View.GONE
                if (mContentView != null) mContentView?.visibility = View.GONE
            }

            VIEW_STATE_ERROR -> {
                if (mErrorView == null) throw NullPointerException("Error View")

                mErrorView?.visibility = View.VISIBLE
                if (mLoadingView != null) mLoadingView?.visibility = View.GONE
                if (mContentView != null) mContentView?.visibility = View.GONE
                if (mEmptyView != null) mEmptyView?.visibility = View.GONE
            }
            else -> {
                // Should never happen, the view should throw an exception if no content view is present upon creation
                if (mContentView == null) throw NullPointerException("Content View")

                mContentView?.visibility = View.VISIBLE
                if (mLoadingView != null) mLoadingView?.visibility = View.GONE
                if (mErrorView != null) mErrorView?.visibility = View.GONE
                if (mEmptyView != null) mEmptyView?.visibility = View.GONE
            }
        }
    }

    fun setViewForState(view: View, state: Int, switchToState: Boolean) {
        when (state) {
            VIEW_STATE_LOADING -> {
                if (mLoadingView != null) removeView(mLoadingView)
                mLoadingView = view
                addView(mLoadingView)
            }
            VIEW_STATE_EMPTY -> {
                if (mEmptyView != null) removeView(mEmptyView)
                mEmptyView = view
                addView(mEmptyView)
            }
            VIEW_STATE_ERROR -> {
                if (mErrorView != null) removeView(mErrorView)
                mErrorView = view
                addView(mErrorView)
            }
            VIEW_STATE_CONTENT -> {
                if (mContentView != null) removeView(mContentView)
                mContentView = view
                addView(mContentView)
            }
        }
        if (switchToState) setViewState(state)
    }

    fun setViewForState(view: View, state: Int) {
        setViewForState(view, state, false)
    }

    fun setViewForState(@LayoutRes layoutRes: Int, state: Int) {
        setViewForState(layoutRes, state, false)
    }

    fun setViewForState(@LayoutRes layoutRes: Int, state: Int, switchToState: Boolean) {
        mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(layoutRes, this, false)
        setViewForState(view, state, switchToState)
    }

}