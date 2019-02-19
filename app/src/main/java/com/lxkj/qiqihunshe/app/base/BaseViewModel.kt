package com.lxkj.qiqihunshe.app.base

import android.app.Activity
import android.databinding.BaseObservable
import android.support.v4.app.Fragment

/**
 * Created by Slingge on 2018/11/13
 */
abstract class BaseViewModel : BaseObservable() {


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