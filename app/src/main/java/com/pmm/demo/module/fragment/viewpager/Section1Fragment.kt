package com.pmm.demo.module.fragment.viewpager

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentActivity
import com.orhanobut.logger.Logger
import com.pmm.demo.module.test.TestActivity
import com.pmm.ui.core.fragment.BaseFragment
import com.pmm.ui.ktx.click

/**
 * Author:你需要一台永动机
 * Date:2019-05-17 10:17
 * Description:
 */
class Section1Fragment : BaseFragment() {


    companion object {

        fun newInstance(activity: FragmentActivity? = null): Section1Fragment {
            if (activity == null) return Section1Fragment()
            val manager = activity.supportFragmentManager
            val fragment = manager.findFragmentByTag(Section1Fragment::class.java.name) as Section1Fragment?
            return fragment ?: Section1Fragment()
        }
    }

    override fun getLayoutUI(): ViewGroup? = LinearLayoutCompat(context).apply {
        this.orientation = LinearLayoutCompat.VERTICAL

        val tv = TextView(context).apply {
            this.text = "测试fragment1"
            this.textSize = 30f
            this.setTextColor(Color.BLACK)
            this.click {
                startActivityForResult(Intent(context, TestActivity::class.java), 1)
            }

        }
        addView(tv)
    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Logger.d("""
            Section1Fragment
            requestCode = $requestCode
            resultCode = $resultCode
            data = $data
        """.trimIndent())
    }


}

