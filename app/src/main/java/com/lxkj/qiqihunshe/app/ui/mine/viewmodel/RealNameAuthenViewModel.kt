package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.ui.mine.model.RealNameAuthenModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.reactivex.Single

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
    fun UpData(): Single<String> {
        abLog.e("实名认证", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val intent = Intent()
                    activity?.let {
                        it.setResult(103, intent)
                        it.finish()
                    }
                }
            }, activity))

    }


}