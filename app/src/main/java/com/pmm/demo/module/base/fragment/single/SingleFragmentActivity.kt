package com.pmm.demo.module.base.fragment.single

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.pmm.demo.base.BaseFragmentActivity

class SingleFragmentActivity() : BaseFragmentActivity() {

    override val fragment: Fragment = TestFragment.newInstance(this)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        super.afterViewAttach(savedInstanceState)
    }
}
