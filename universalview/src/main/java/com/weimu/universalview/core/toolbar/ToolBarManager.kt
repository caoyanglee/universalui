package com.weimu.universalview.core.toolbar

import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.weimu.universalib.OriginAppData
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalview.ktx.setHeight
import com.weimu.universalview.ktx.setOnClickListenerPro
import com.weimu.universalview.ktx.visible
import kotlinx.android.synthetic.main.include_toolbar_primary.view.*


class ToolBarManager private constructor(private val mActivity: AppCompatActivity, private val mContent: ViewGroup) {

    private var mContainer: ViewGroup? = null
    /**
     * 获取标题视图
     * @备注 若有特殊处理，请获取此视图进行修改
     */
    private var mTitle: TextView? = null
    private var mMenuTextViewLeft: TextView? = null
    private var mMenuTextViewRight: TextView? = null
    private var mMenuTextViewRight2: TextView? = null
    private var mMenuIconLeft: ImageView? = null

    private var actionMenu1: ImageView? = null
    private var actionMenu2: ImageView? = null


    companion object {
        fun with(mActivity: AppCompatActivity, mContent: ViewGroup) = ToolBarManager(mActivity, mContent)
    }

    init {
        initToolbar(this.mContent)
    }

    private fun initToolbar(mContent: ViewGroup?) {
        if (mContent == null) {
            return
        }

        //mContainer
        mContainer = mContent.cl_toolbar
        mContainer?.setPadding(DefaultConfig.toolbarPadding, 0, DefaultConfig.toolbarPadding, 0)
        mContainer?.setHeight(DefaultConfig.toolbarHeight)


        //mTitle
        mTitle = mContent.toolbar_title
        actionMenu1 = mContent.toolbar_menu_icon_right
        actionMenu2 = mContent.toolbar_menu_icon_right_v2
        mMenuIconLeft = mContent.toolbar_menu_icon_left
        mMenuIconLeft?.setOnClickListenerPro {
            mActivity.onBackPressed()
        }


        //textMenu
        mMenuTextViewRight = mContent.toolbar_menu_text_right
        mMenuTextViewRight2 = mContent.toolbar_menu_text_right_v2
        mMenuTextViewLeft = mContent.toolbar_menu_text_left
    }

    /**
     * 设置左边的按钮Icon
     * 备注：若不想显示图片，可以输入-1或者0
     */
    @Deprecated("尽量使用通用方法 @link leftMenuIcon")
    fun setNavigationIcon(@DrawableRes resId: Int) = this.apply {
        if (resId != 0 && resId != -1) {
            mMenuIconLeft?.setImageResource(resId)
            mMenuIconLeft?.visibility = View.VISIBLE
        } else {
            mMenuIconLeft?.visibility = View.GONE
        }
    }


    //通用设置 设置背景
    fun bg(config: (ViewGroup.() -> Unit)) = this.apply {
        mContainer?.config()
    }

    //设置 背景 color
    @Deprecated("尽量使用通用方法")
    fun setBackgroundColor(@ColorRes color: Int) = this.apply {
        mContainer?.setBackgroundColor(ContextCompat.getColor(mActivity, color))
    }

    //设置 背景 drawable
    @Deprecated("尽量使用通用方法")
    fun setBackground(background: Drawable) = this.apply {
        mContainer?.background = background
    }


    //设置 背景 DrawableRes
    @Deprecated("尽量使用通用方法")
    fun setBackground(@DrawableRes drawableRes: Int) = this.apply {
        mContainer?.background = ContextCompat.getDrawable(mActivity, drawableRes)
    }


    //通用设置 中间标题方法
    fun title(config: (TextView.() -> Unit)) = this.apply {
        mTitle?.visible()
        mTitle?.config()
    }

    //设置标题内容
    @Deprecated("尽量使用通用方法")
    fun setTitle(consequence: CharSequence) = this.apply {
        mTitle?.visible()
        mTitle?.text = consequence
    }


    //设置标题颜色
    @Deprecated("尽量使用通用方法")
    fun setTitleColor(@ColorRes color: Int) = this.apply {
        mTitle?.visible()
        mTitle?.setTextColor(ContextCompat.getColor(mActivity, color))
    }

    //设置标题字体大小
    @Deprecated("尽量使用通用方法")
    fun setTitleSize(size: Float) = this.apply {
        mTitle?.visible()
        mTitle?.textSize = size
    }

    //通用设置 左侧文本菜单
    fun leftMenuText(config: (TextView.() -> Unit)) = this.apply {
        setNavigationIcon(-1)
        mMenuTextViewLeft?.config()
        mMenuTextViewLeft?.visibility = View.VISIBLE
    }


    //设置【左侧文本菜单】的内容
    @Deprecated("尽量使用通用方法")
    fun setLeftMenuTextContent(consequence: CharSequence) = this.apply {
        setNavigationIcon(-1)
        mMenuTextViewLeft?.visibility = View.VISIBLE
        mMenuTextViewLeft?.setOnClickListener(null)
        mMenuTextViewLeft?.text = consequence
    }


    //设置【左侧文本菜单】标题颜色
    @Deprecated("尽量使用通用方法")
    fun setLeftMenuTextColor(@ColorRes color: Int) = this.apply {
        mMenuTextViewLeft?.visibility = View.VISIBLE
        mMenuTextViewLeft?.setTextColor(ContextCompat.getColor(mActivity, color))
    }

    //设置【左侧文本菜单】点击事件
    @Deprecated("尽量使用通用方法")
    fun setLeftMenuTextClick(onclick: ((View) -> Unit)) = this.apply {
        mMenuTextViewLeft?.setOnClickListenerPro(onclick)
    }

    //通用设置 右侧文本菜单
    fun rightMenuText(config: (TextView.() -> Unit)) = this.apply {
        mMenuTextViewRight?.config()
        mMenuTextViewRight?.visibility = View.VISIBLE
    }

    //设置【右侧文本菜单】的内容
    @Deprecated("尽量使用通用方法")
    fun setRightMenuTextContent(consequence: CharSequence) = this.apply {
        mMenuTextViewRight?.visibility = View.VISIBLE
        mMenuTextViewRight?.text = consequence
    }


    // 设置【右侧文本菜单】标题颜色
    @Deprecated("尽量使用通用方法")
    fun setRightMenuTextColor(@ColorRes color: Int) = this.apply {
        mMenuTextViewRight?.visibility = View.VISIBLE
        mMenuTextViewRight?.setTextColor(ContextCompat.getColor(mActivity, color))
    }

    //设置【右侧文本菜单】点击事件
    @Deprecated("尽量使用通用方法")
    fun setRightMenuTextClick(onclick: ((View) -> Unit)) = this.apply {
        mMenuTextViewRight?.setOnClickListenerPro(onclick)
    }


    //通用设置 右侧文本菜单
    fun rightMenuText2(config: (TextView.() -> Unit)) = this.apply {
        mMenuTextViewRight2?.config()
        mMenuTextViewRight2?.visibility = View.VISIBLE
    }

    //设置【右侧文本菜单】的内容
    @Deprecated("尽量使用通用方法")
    fun setRightMenuText2Content(consequence: CharSequence) = this.apply {
        mMenuTextViewRight2?.visibility = View.VISIBLE
        mMenuTextViewRight2?.text = consequence
    }


    //设置【右侧文本菜单】标题颜色
    @Deprecated("尽量使用通用方法")
    fun setRightMenuText2Color(@ColorRes color: Int) = this.apply {
        mMenuTextViewRight2?.visibility = View.VISIBLE
        mMenuTextViewRight2?.setTextColor(ContextCompat.getColor(mActivity, color))
    }

    //设置【右侧文本菜单】点击事件
    @Deprecated("尽量使用通用方法")
    fun setRightMenuText2Click(onclick: ((View) -> Unit)) = this.apply {
        mMenuTextViewRight2?.setOnClickListenerPro(onclick)
    }


    //通用设置 右侧图标菜单
    fun rightMenuIcon(config: (ImageView.() -> Unit)) = this.apply {
        actionMenu1?.config()
        actionMenu1?.visibility = View.VISIBLE
    }

    //设置 右侧icon
    @Deprecated("尽量使用通用方法")
    fun setRightMenuIconRes(@DrawableRes resId: Int) = this.apply {
        actionMenu1?.visibility = View.VISIBLE
        actionMenu1?.setImageResource(resId)
    }

    //设置 右侧icon点击事件
    @Deprecated("尽量使用通用方法")
    fun setRightMenuIconClickListener(onclick: ((View) -> Unit)) = this.apply {
        actionMenu1?.setOnClickListenerPro(onclick)
    }

    //通用设置 右侧2图标菜单
    fun rightMenuIcon2(config: (ImageView.() -> Unit)) = this.apply {
        actionMenu2?.config()
        actionMenu2?.visibility = View.VISIBLE
    }

    //设置 右侧icon2
    @Deprecated("尽量使用通用方法")
    fun setRightMenuIcon2Res(@DrawableRes resId: Int) = this.apply {
        actionMenu2?.visibility = View.VISIBLE
        actionMenu2?.setImageResource(resId)

    }

    //设置 右侧icon2点击事件
    @Deprecated("尽量使用通用方法")
    fun setRightMenuIcon2ClickListener(onclick: ((View) -> Unit)) = this.apply {
        actionMenu2?.setOnClickListenerPro(onclick)
    }


    //通用设置 左侧icon
    fun leftMenuIcon(config: (ImageView.() -> Unit)) = this.apply {
        mMenuIconLeft?.config()
        mMenuIconLeft?.visibility = View.VISIBLE
    }


    //隐藏工具栏
    fun hideToolBar() = this.apply { mContainer?.visibility = View.GONE }

    //显示工具栏
    fun showToolBar() = this.apply { mContainer?.visibility = View.VISIBLE }


    //默认配置
    object DefaultConfig {
        var toolbarPadding = OriginAppData.context.dip2px(16f)//父视图的左右padding
        var toolbarHeight = OriginAppData.context.dip2px(48f)//父视图的高度
    }
}

