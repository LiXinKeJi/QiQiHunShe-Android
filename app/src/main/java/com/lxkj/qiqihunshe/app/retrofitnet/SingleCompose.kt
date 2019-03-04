package com.lxkj.qiqihunshe.app.retrofitnet

import android.accounts.Account
import android.app.Activity
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.ThreadUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.reactivex.SingleTransformer
import org.json.JSONObject
import com.lxkj.qiqihunshe.app.util.GenericsUtils


/**
 * Created by Slingge on 2019/1/9
 */
object SingleCompose {


    fun compose(SingleObserver: SingleObserverInterface, context: Activity?): SingleTransformer<String, String> {
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
                abLog.e("返回数据", t!!)
                val obj = JSONObject(t)
                if (obj.getString("result") == "0") {
                    val type = GenericsUtils.getSuperClassGenricType(Account::class.java, 0)
                    SingleObserver.onSuccess(t)
                } else {
                    ToastUtil.showTopSnackBar(context, obj.getString("resultNote"))
                }
            }.doOnError {
                try {
                    ThreadUtil.runOnMainThread(Runnable {
                        ToastUtil.showTopSnackBar(context, "网络错误")
                    })
                } catch (e: Exception) {
                }
            }
        }
    }



    fun <T> compose(context: Activity?): SingleTransformer<T, T> {
        return return SingleTransformer { upstream ->
            upstream.doOnSubscribe {
                //                ToastUtil.showToast("开始")
                context?.let {
                    ProgressDialogUtil.showProgressDialog(it)
                }
            }.doAfterSuccess {
                //                ToastUtil.showToast("结束")
                ProgressDialogUtil.dismissDialog()
            }.doOnError {
                it.message?.let {
                    ToastUtil.showToast(it)
                }
            }
        }
    }


}