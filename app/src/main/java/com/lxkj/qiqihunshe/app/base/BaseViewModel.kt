package com.lxkj.qiqihunshe.app.base

import android.app.Activity
import android.databinding.BaseObservable
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil

/**
 * Created by Slingge on 2018/11/13
 */
open class BaseViewModel : BaseObservable() {

    val retrofit by lazy { RetrofitUtil.getRetrofitApi().create(RetrofitService::class.java) }

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


}