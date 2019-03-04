package com.lxkj.qiqihunshe.app.retrofitnet

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.ThreadUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.orhanobut.logger.Logger
import io.reactivex.SingleTransformer
import java.lang.reflect.ParameterizedType

/**
 * Created by Slingge on 2019/1/9
 */
object SingleCompose {


    fun <T> compose(SingleObserver: SingleObserverInterface<T>, context: Activity?): SingleTransformer<String, String> {
        return return SingleTransformer { upstream ->
            upstream.doOnSubscribe {
                //                ToastUtil.showToast("开始")
                context?.let {
                    ProgressDialogUtil.showProgressDialog(it)
                }
            }.doAfterSuccess {
                //                ToastUtil.showToast("结束")
                ProgressDialogUtil.dismissDialog()
            }.doOnSuccess { t: String? ->
                val type = object : TypeToken<T>() {}.type
                val bean = Gson().fromJson<T>(t,type)
                abLog.e("返回数据", Gson().toJson(bean))
                bean?.let {
                    if ((Gson().fromJson(t, BaseModel::class.java)).result == "0") {
                        SingleObserver.onSuccess(it)
                    } else {
                        ToastUtil.showTopSnackBar(context, (Gson().fromJson(t, BaseModel::class.java)).resultNote)
                    }
                }
            }.doOnError {
                try {
                    ThreadUtil.runOnMainThread(Runnable {
                        ToastUtil.showTopSnackBar(context, "网络错误")
                    })
                }catch (e:Exception){
                }
            }
        }
    }


    fun <T> compose(SingleObserver: SingleObserverInterface<T>): SingleTransformer<T, T> {
        return return SingleTransformer { upstream ->
            upstream.doOnSubscribe {
                //                ToastUtil.showToast("开始")

            }.doAfterSuccess {
                //                ToastUtil.showToast("结束")

            }.doOnSuccess { t: T? ->
                t?.let {
                    Logger.e("数据：\n" + Gson().toJson(it))
                    if ((it as BaseModel).result == "0") {
                        SingleObserver.onSuccess(t)
                    } else {
                        Logger.e("数据错误", Gson().toJson(it))
                        ToastUtil.showToast("数据错误")
                    }
                }
            }.doOnError {
                it.message?.let {
                    ToastUtil.showToast(it)
                }
            }
        }
    }

}