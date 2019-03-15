package com.lxkj.qiqihunshe.app.retrofitnet.exception

import android.app.Activity
import android.arch.lifecycle.*
import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.FlowableSubscribeProxy
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

/**
 * 页面描述：一些扩展
 *
 * Created by ditclear on 2017/9/29.
 */

fun Activity.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun dispatchFailure(activity: Activity, error: Throwable?) {
    error?.let {
        if (abLog.E) {
            it.printStackTrace()
        }
        if (it is SocketTimeoutException) {
            it.message?.let { ToastUtil.showTopSnackBar(activity, "网络连接超时") }
        } else if (it is UnknownHostException || it is ConnectException) {
            //网络未连接
            it.message?.let { ToastUtil.showTopSnackBar(activity, "连接服务器失败") }
        } else {
            it.message?.let { ToastUtil.showTopSnackBar(activity, it) }
        }
    }
}

fun dispatchFailure(error: Throwable?) {
    error?.let {
        if (abLog.E) {
            it.printStackTrace()
        }
        if (it is SocketTimeoutException) {
            it.message?.let { ToastUtil.showToast( "网络连接超时") }
        } else if (it is UnknownHostException || it is ConnectException) {
            //网络未连接
            it.message?.let { ToastUtil.showToast( "连接服务器失败") }
        } else {
            it.message?.let { ToastUtil.showToast( it) }
        }
    }
}

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

fun <T> Flowable<T>.bindLifeCycle(owner: LifecycleOwner): FlowableSubscribeProxy<T> =
    this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))


fun <T : Any> FragmentActivity.argument(key: String) =
    lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }


fun Activity.dpToPx(@DimenRes resID: Int): Int = this.resources.getDimensionPixelOffset(resID)


//////////////////////////LiveData///////////////////////////////////

fun <T> MutableLiveData<T>.set(t: T?) = this.postValue(t)
fun <T> MutableLiveData<T>.get() = this.value

fun <T> MutableLiveData<T>.get(t: T): T = get() ?: t

fun <T> MutableLiveData<T>.init(t: T) = MutableLiveData<T>().apply {
    postValue(t)
}

fun <T> LiveData<T>.toFlowable(): Flowable<T> = Flowable.create({ emitter ->
    val observer = Observer<T> { data ->
        data?.let { emitter.onNext(it) }
    }
    observeForever(observer)

    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeObserver(observer)
        }
    }
}, BackpressureStrategy.LATEST)

//////////////////////////DataBinding///////////////////////////////////
fun <T> ObservableField<T>.toFlowable(): Flowable<T> = Flowable.create({ emitter ->
    val observer = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            get()?.let { emitter.onNext(it) }
        }
    }
    addOnPropertyChangedCallback(observer)

    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeOnPropertyChangedCallback(observer)
        }
    }
}, BackpressureStrategy.LATEST)

fun ObservableBoolean.toFlowable(): Flowable<Boolean> = Flowable.create({ emitter ->
    val observer = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            emitter.onNext(get())
        }
    }
    addOnPropertyChangedCallback(observer)

    emitter.setCancellable {
        object : MainThreadDisposable() {
            override fun onDispose() = removeOnPropertyChangedCallback(observer)
        }
    }
}, BackpressureStrategy.LATEST)