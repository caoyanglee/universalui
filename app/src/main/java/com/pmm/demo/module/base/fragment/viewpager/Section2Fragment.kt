package com.pmm.demo.module.base.fragment.viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentActivity
import com.orhanobut.logger.Logger
import com.pmm.ui.core.fragment.BaseFragment

/**
 * Author:你需要一台永动机
 * Date:2019-05-17 10:17
 * Description:
 */
class Section2Fragment : BaseFragment() {


    companion object {

        fun newInstance(activity: FragmentActivity? = null): Section2Fragment {
            if (activity == null) return Section2Fragment()
            val manager = activity.supportFragmentManager
            val fragment = manager.findFragmentByTag(Section2Fragment::class.java.name) as Section2Fragment?
            return fragment ?: Section2Fragment()
        }
    }

    override fun getLayoutUI(): ViewGroup? = LinearLayoutCompat(requireContext()).apply {
        this.orientation = LinearLayoutCompat.VERTICAL

        val tv = TextView(context).apply {
            this.text = "测试fragment2"
            this.textSize = 30f
            this.setTextColor(Color.BLACK)
        }

        addView(tv)
    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {

    }

    override fun onViewPagerFirstShow() {
        super.onViewPagerFirstShow()
        Logger.d("Section1Fragment 1")
    }
}