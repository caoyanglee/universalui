package com.pmm.demo.module.base.fragment.single

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentActivity
import com.pmm.ui.core.fragment.BaseFragment

/**
 * Author:你需要一台永动机
 * Date:2019-05-17 10:17
 * Description:
 */
class TestFragment : BaseFragment() {

    companion object {

        fun newInstance(activity: FragmentActivity? = null): TestFragment {
            if (activity == null) return TestFragment()
            val manager = activity.supportFragmentManager
            val fragment = manager.findFragmentByTag(TestFragment::class.java.name) as TestFragment?
            return fragment ?: TestFragment()
        }
    }

    override fun getLayoutUI(): ViewGroup? = LinearLayoutCompat(requireContext()).apply {
        this.orientation = LinearLayoutCompat.VERTICAL

        val tv = TextView(context).apply {
            this.text = "测试fragment"
            this.textSize = 30f
        }

        addView(tv)
    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {

    }
}