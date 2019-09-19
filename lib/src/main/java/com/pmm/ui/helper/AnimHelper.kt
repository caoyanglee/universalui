package com.pmm.ui.helper

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

/**
 * Author:你需要一台永动机
 * Date:2018/3/15 11:03
 * Description:
 */

object AnimHelper {

    //渐变
    fun alphaAnim(view: View, duration: Long,
                  onAnimEnd: ((Animator) -> Unit)? = null,
                  onAnimCancel: ((Animator) -> Unit)? = null) {
        val anim = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f)
        anim.duration = duration
        anim.repeatCount = 0
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator) {}
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                onAnimEnd?.invoke(animation)
            }

            override fun onAnimationCancel(animation: Animator) {
                onAnimCancel?.invoke(animation)
            }
        })
        anim.start()
    }

    fun scaleAnim(view: View) {
        val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f)
        scaleXAnimator.duration = 500
        scaleXAnimator.repeatCount = 1
        scaleXAnimator.repeatMode = ValueAnimator.REVERSE
        scaleXAnimator.start()
    }

    fun rotationAnim(view: View) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        objectAnimator.duration = 500
        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.start()
    }

    fun translateAnim(view: View) {
        val objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 0f, 100f)
        objectAnimator.duration = 500
        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.start()
    }

    fun animSet(view: View) {
        val animatorSet = AnimatorSet()

        val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f)
        scaleXAnimator.duration = 500
        scaleXAnimator.repeatCount = 1
        scaleXAnimator.repeatMode = ValueAnimator.REVERSE
        scaleXAnimator.start()

        val scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f)
        scaleYAnimator.duration = 500
        scaleYAnimator.repeatCount = 1
        scaleYAnimator.repeatMode = ValueAnimator.REVERSE

        animatorSet.playTogether(scaleXAnimator, scaleYAnimator)
        animatorSet.start()
    }
}
