package com.weimu.universalview.helper

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RxSchedulers {
    companion object {
        fun <T> toMain(): ObservableTransformer<T, T> {
            return ObservableTransformer<T, T> { upstream ->
                upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }


}