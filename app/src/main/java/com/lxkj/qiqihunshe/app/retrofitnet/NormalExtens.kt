package com.lxkj.qiqihunshe.app.retrofitnet

import android.arch.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 页面描述：NormalExtens
 *
 * Created by ditclear on 2017/11/19.
 */
//请求延迟时间
fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
    this.subscribeOn(Schedulers.io())
        .delay(withDelay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(): Single<T> =
    this.subscribeOn(Schedulers.io())
//                .delay(withDelay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())

//绑定生命周期
fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> {
    return this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
}





