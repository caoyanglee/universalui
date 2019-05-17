package com.weimu.app.universalview.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.weimu.app.universalview.R


/**
 * 专门显示fragment的activity的基类
 */
abstract class BaseFragmentActivity : BaseViewActivity() {

    protected abstract val fragment: Fragment

    override fun getLayoutUI(): ViewGroup? = BaseFragmentUI(this)

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commitAllowingStateLoss()

    }
}

class BaseFragmentUI : LinearLayoutCompat {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        orientation = LinearLayout.VERTICAL

        val frameLayout = FrameLayout(context)
        frameLayout.id = R.id.fragment_container

        addView(frameLayout)

        val targetLayoutParams = (frameLayout.layoutParams as LinearLayoutCompat.LayoutParams).apply {
            width = LinearLayoutCompat.LayoutParams.MATCH_PARENT
            height = LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        }
        frameLayout.layoutParams = targetLayoutParams
    }


}