package com.weimu.app.universalview.module.fragment.single

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.weimu.app.universalview.base.BaseFragmentActivity

class SingleFragmentActivity() : BaseFragmentActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SingleFragmentActivity::class.java)
        }
    }

    override val fragment: Fragment = TestFragment.newInstance(this)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        super.afterViewAttach(savedInstanceState)
    }
}
