package com.pmm.ui.ktx

import android.animation.ObjectAnimator
import android.os.Handler
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.RecyclerView


/**
 * @param views 目标视图
 * @param scrollHeight 滚动的距离 单位px
 */
fun RecyclerView.addScrollToBottomAnimation(vararg views: View, scrollHeight: Float = 0f, animationTime: Long = 200) {
    var isViewGone = false
    var isAnimationRun = false

    fun scroll2Bottom() {
        if (isAnimationRun || isViewGone) return
        isAnimationRun = true
        for (item in views) {
            val animator = ObjectAnimator.ofFloat(item, "translationY", 0f, scrollHeight).setDuration(animationTime)
            animator.interpolator = AccelerateInterpolator()
            animator.start()//图片
        }
        Handler().postDelayed({
            isAnimationRun = false
            isViewGone = true
        }, animationTime)
    }

    fun scroll2Top() {
        if (isAnimationRun || !isViewGone) return
        isAnimationRun = true
        for (item in views) {
            val animator = ObjectAnimator.ofFloat(item, "translationY", scrollHeight, 0f).setDuration(animationTime)
            animator.interpolator = AccelerateInterpolator()
            animator.start()//图片
        }
        Handler().postDelayed({
            isAnimationRun = false
            isViewGone = false
        }, animationTime)
    }

    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (Math.abs(dy) <= 20) return//偏移量必须大于20
            if (dy > 0) {
                scroll2Bottom()//上拉
            } else {
                scroll2Top() //下拉
            }
        }
    })
}