package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.entrance.WelComeActivity
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivitySigninBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/16
 */
class SignInViewModel : BaseViewModel() {

    val headerUrl = ObservableField<String>()

    var bind: ActivitySigninBinding? = null


    fun sginIn(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "登录成功")
                    val model = Gson().fromJson(response, SignInModel::class.java)
                    StaticUtil.uid = model.uid
                    StaticUtil.fill = model.fill// 0未完善资料 1已完善资料
                    StaticUtil.rytoken = model.rytoken

                    val sp = activity!!.getSharedPreferences(SharedPreferencesUtil.NAME, 0)
                    sp.edit().putString("uid", model.uid).putString("rytoken", model.rytoken).commit()
                    RongYunUtil.initService()
                    if (model.fill == "0") {
                        val bundle = Bundle()
                        bundle.putInt("state", 2)
                        MyApplication.openActivity(activity, PerfectInfoActivitiy::class.java, bundle)
                    } else {
                        MyApplication.openActivity(activity, MainActivity::class.java)
                    }
                    activity?.finish()
                }
            }, activity))


}