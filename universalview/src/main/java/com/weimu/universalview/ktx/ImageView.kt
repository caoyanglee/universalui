package com.weimu.universalview.ktx

import android.app.Activity
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.weimu.universalib.R
import com.weimu.universalib.helper.glide.GlideRoundTransform
import java.io.File

/**
 * Author:你需要一台永动机
 * Date:2018/5/21 09:26
 * Description:
 */

//图片加载 normal
fun ImageView.load(url: String = "", placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(url)
            .apply(RequestOptions().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))

    requestBuilder.into(this)
}

fun ImageView.load(drawableRes: Int = 0, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(drawableRes)
            .apply(RequestOptions().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))

    requestBuilder.into(this)
}


fun ImageView.load(file: File, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(file)
            .apply(RequestOptions().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


fun ImageView.load(bitmap: Bitmap, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(bitmap)
            .apply(RequestOptions().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


//图片加载 centerCrop
fun ImageView.load4CenterCrop(url: String = "", placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(url)
            .apply(RequestOptions().centerCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}

fun ImageView.load4CenterCrop(drawableRes: Int = 0, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(drawableRes)
            .apply(RequestOptions().centerCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


fun ImageView.load4CenterCrop(file: File, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(file)
            .apply(RequestOptions().centerCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


fun ImageView.load4CenterCrop(bitmap: Bitmap, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(bitmap)
            .apply(RequestOptions().centerCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}

//图片加载 circle
fun ImageView.load4Circle(url: String = "", placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(url)
            .apply(RequestOptions().circleCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}

fun ImageView.load4Circle(drawableRes: Int = 0, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(drawableRes)
            .apply(RequestOptions().circleCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


fun ImageView.load4Circle(file: File, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(file)
            .apply(RequestOptions().circleCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


fun ImageView.load4Circle(bitmap: Bitmap, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(bitmap)
            .apply(RequestOptions().circleCrop().placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))

    requestBuilder.into(this)
}


//图片加载 round
fun ImageView.load4Round(url: String = "", radius: Int=6, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(url)
            .apply(RequestOptions().transform(GlideRoundTransform(radius)).placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}

fun ImageView.load4Round(drawableRes: Int = 0, radius: Int=6, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(drawableRes)
            .apply(RequestOptions().transform(GlideRoundTransform(radius)).placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))

    requestBuilder.into(this)
}


fun ImageView.load4Round(file: File, radius: Int=6, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(file)
            .apply(RequestOptions().transform(GlideRoundTransform(radius)).placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}


fun ImageView.load4Round(bitmap: Bitmap, radius: Int=6, placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val requestBuilder = Glide.with(context)
            .load(bitmap)
            .apply(RequestOptions().transform(GlideRoundTransform(radius)).placeholder(placeholder))
    if (useAnim) requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))
    requestBuilder.into(this)
}



//不进行缓存的图片请求
fun ImageView.load4NoCache(url: String = "", placeholder: Int = -1, useAnim: Boolean = true) {
    if (context is Activity) {
        if ((context as Activity).isFinishing) return
    }
    val options = RequestOptions()
            .placeholder(placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
    val requestBuilder = Glide.with(context)
            .load(url)
            .apply(options)
    if (useAnim)
        requestBuilder.transition(GenericTransitionOptions.with(R.anim.fade_in))

    requestBuilder.into(this)
}

