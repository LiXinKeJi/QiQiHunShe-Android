package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.databinding.ObservableField
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
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
                    StaticUtil.fill = model.fill
                    if (model.fill == "0") {
                        MyApplication.openActivity(activity, MainActivity::class.java)

                       // MyApplication.openActivity(activity, PerfectInfoActivitiy::class.java)
                    } else {
                        MyApplication.openActivity(activity, MainActivity::class.java)
                    }
                    activity?.finish()
                }
            }, activity))


}