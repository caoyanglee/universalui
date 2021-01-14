package com.pmm.demo.module.base.fragment.single

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pmm.demo.base.BaseFragmentActivity

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
