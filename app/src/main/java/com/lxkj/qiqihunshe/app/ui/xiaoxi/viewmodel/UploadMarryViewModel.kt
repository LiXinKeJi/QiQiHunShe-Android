package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.DynamicSignUpAfterDialog
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.UploadMarryModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityUploadMarryBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/3/1
 */
class UploadMarryViewModel : BaseViewModel(), UpLoadFileCallBack {

    val marryModel by lazy { UploadMarryModel() }

    private val upload by lazy { UpFileUtil(activity!!, this) }

    var flag = "1"//1牵手 2离婚
    var bind: ActivityUploadMarryBinding?=null


    fun init() {

        if (marryModel.type == "2") {
            bind?.let {
                it.upload.text="请拍摄/上传您的离婚证"
                it.tvAdd.text="上传离婚证"

            }
        }

    }


    fun loadfile(path: String) {
        upload.upLoad(path)
    }


    override fun uoLoad(url: List<String>) {

    }

    override fun uoLoad(url: String) {
        marryModel.image = url
    }


    fun qianshou(): Single<String> {
        return retrofit.getData(Gson().toJson(marryModel)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("牵手成功")
                    DynamicSignUpAfterDialog.sginUpShow(activity!!, "已提交管理员审核，请耐心等待")
                }
            }, activity))
    }


}