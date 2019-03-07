package com.lxkj.qiqihunshe.app.base

import android.app.Activity
import android.databinding.BaseObservable
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil

/**
 * Created by Slingge on 2018/11/13
 */
open class BaseViewModel : BaseObservable() {

    val retrofit by lazy { RetrofitUtil.getRetrofit().create(RetrofitService::class.java) }

    var activity: Activity? = null
    var fragment: Fragment? = null

    fun detachView() {
        activity?.let {
            activity = null
        }
        fragment?.let {
            fragment = null
        }
    }

    //网络异常捕捉
    fun toastFailure(error: Throwable?) {
        ProgressDialogUtil.dismissDialog()
        if (activity == null) {
            dispatchFailure(fragment!!.activity!!, error)
        } else {
            dispatchFailure(activity!!, error)
        }

    }


}