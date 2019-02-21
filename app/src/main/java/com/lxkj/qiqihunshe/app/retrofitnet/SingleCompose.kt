package com.lxkj.qiqihunshe.app.retrofitnet

import android.content.Context
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.orhanobut.logger.Logger
import io.reactivex.SingleTransformer

/**
 * Created by Slingge on 2019/1/9
 */
object SingleCompose {


    fun <T> compose(SingleObserver: SingleObserverInterface<T>, context: Context?): SingleTransformer<T, T> {
        return return SingleTransformer { upstream ->
            upstream.doOnSubscribe {
                //                ToastUtil.showToast("开始")
                context?.let {
                    ProgressDialogUtil.showProgressDialog(it)
                }
            }.doAfterSuccess {
                //                ToastUtil.showToast("结束")
                ProgressDialogUtil.dismissDialog()
            }.doOnSuccess { t: T? ->
                t?.let {
                    SingleObserver.onSuccess(t)
                    Logger.e("数据：\n" + Gson().toJson(it))
                    if ((it as BaseModel).result == "0") {
                        SingleObserver.onSuccess(t)
                    } else {
                        Logger.e("数据错误", Gson().toJson(it))
                        ToastUtil.showToast("数据错误")
                    }
                }
            }.doOnError {
                ToastUtil.showToast(it.toString())
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
                    SingleObserver.onSuccess(t)
                    Logger.e("数据：\n" + Gson().toJson(it))
                    if ((it as BaseModel).result == "0") {
                        SingleObserver.onSuccess(t)
                    } else {
                        Logger.e("数据错误", Gson().toJson(it))
                        ToastUtil.showToast("数据错误")
                    }
                }
            }.doOnError {
                ToastUtil.showToast(it.toString())
            }
        }
    }

}