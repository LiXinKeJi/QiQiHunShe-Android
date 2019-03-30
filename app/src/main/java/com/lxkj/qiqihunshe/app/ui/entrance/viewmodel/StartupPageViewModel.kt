package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.os.CountDownTimer
import android.text.TextUtils
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.SignInActivity
import com.lxkj.qiqihunshe.app.util.StaticUtil

/**
 * Created by Slingge on 2019/3/25
 */
class StartupPageViewModel : BaseViewModel() {


    val timer = object : CountDownTimer(3000, 1000) {
        override fun onTick(p0: Long) {
        }

        override fun onFinish() {
            if(TextUtils.isEmpty(StaticUtil.uid)){
                MyApplication.openActivity(activity, SignInActivity::class.java)
            }else{
                MyApplication.openActivity(activity, MainActivity::class.java)
            }
            activity?.finish()
        }
    }


}