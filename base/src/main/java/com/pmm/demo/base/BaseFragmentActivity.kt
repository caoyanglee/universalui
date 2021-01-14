package com.pmm.demo.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment


/**
 * 专门显示fragment的activity的基类
 */
abstract class BaseFragmentActivity : BaseViewActivity(), BaseViewInit {

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
        orientation = VERTICAL

        val frameLayout = FrameLayout(context)
        frameLayout.id = R.id.fragment_container

        addView(frameLayout)

        val targetLayoutParams = (frameLayout.layoutParams as LayoutParams).apply {
            width = LayoutParams.MATCH_PARENT
            height = LayoutParams.WRAP_CONTENT
        }
        frameLayout.layoutParams = targetLayoutParams
    }

}