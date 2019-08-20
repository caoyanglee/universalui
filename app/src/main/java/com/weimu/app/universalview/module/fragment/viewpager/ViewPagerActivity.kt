package com.weimu.app.universalview.module.fragment.viewpager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.pager.BaseFragmentPagerAdapter
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : BaseViewActivity() {

    override fun getLayoutResID(): Int = R.layout.activity_view_pager

    companion object {
        fun newIntent(context: Context) = Intent(context, ViewPagerActivity::class.java)
    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolbar.with(this)
                .centerTitle {
                    this.text = "ViewPager"
                }.navigationIcon {
                    this.setOnClickListenerPro { onBackPressed() }
                }


        viewpager.adapter = BaseFragmentPagerAdapter(supportFragmentManager).apply {
            val fragments = arrayListOf<Fragment>()
            fragments.add(Section1Fragment.newInstance(this@ViewPagerActivity))
            fragments.add(Section2Fragment.newInstance(this@ViewPagerActivity))
            fragments.add(Section3Fragment.newInstance(this@ViewPagerActivity))
            setFragments(fragments)
        }
        viewpager.offscreenPageLimit = 3
    }
}
