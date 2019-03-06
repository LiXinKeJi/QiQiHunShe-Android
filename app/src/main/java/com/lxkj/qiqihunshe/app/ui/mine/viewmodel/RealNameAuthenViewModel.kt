package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.text.TextUtils
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.ui.mine.model.RealNameAuthenModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog

/**
 * Created by Slingge on 2019/2/20
 */
class RealNameAuthenViewModel : BaseViewModel(), UpLoadFileCallBack {

    val model by lazy { RealNameAuthenModel() }

    var Questions = false//是否问答

    private val upload by lazy { UpFileUtil(activity!!, this) }


    fun upFile(path: String) {
        upload.upLoad(path)
    }

    override fun uoLoad(url: List<String>) {

    }

    override fun uoLoad(url: String) {
        ProgressDialogUtil.dismissDialog()
        abLog.e("上传视频", url)
        model.video = url
    }

    @SuppressLint("CheckResult")
    fun UpData() {
        if (TextUtils.isEmpty(model.video)) {
            ToastUtil.showTopSnackBar(activity, "请选择3秒短视频")
            return
        }
        if (TextUtils.isEmpty(model.idnumber)) {
            ToastUtil.showTopSnackBar(activity, "请选择身份证照")
            return
        }
        if (TextUtils.isEmpty(model.car)) {
            ToastUtil.showTopSnackBar(activity, "请选择驾驶证照")
            return
        }
        if (TextUtils.isEmpty(model.house)) {
            ToastUtil.showTopSnackBar(activity, "请选择房产证照")
            return
        }
        if (TextUtils.isEmpty(model.salary)) {
            ToastUtil.showTopSnackBar(activity, "请选择工作证照")
            return
        }
        if (TextUtils.isEmpty(model.education)) {
            ToastUtil.showTopSnackBar(activity, "请选择学历证书")
            return
        }


        retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "提交成功")
                    activity?.finish()
                }
            }, activity))
            .subscribe({}, { toastFailure(it) })

    }


}