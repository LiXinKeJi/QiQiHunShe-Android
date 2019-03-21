package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.databinding.ObservableField
import android.text.TextUtils
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.QianShouRenModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentQianshouBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/3/1
 */
class QianShouViewModel : BaseViewModel() {

    val icon = ObservableField<String>()
    val name = ObservableField<String>()

    val userIcon = ObservableField<String>()
    val userName = ObservableField<String>()

    var bind: FragmentQianshouBinding? = null

    var qianshouId = ""//牵手人id
    var flag = "1"//1牵手 2离婚


    fun init() {
        userIcon.set(StaticUtil.headerUrl)
        userName.set(StaticUtil.nickName)
        name.set("选择牵手人")
    }

    fun getQianShou(): Single<String> {
        val json = "{\"cmd\":\"getQianshou\",\"uid\":\"" + StaticUtil.uid + "\"}"
        abLog.e("获取牵手人", json)
        return retrofit.getData(json).async()
            .doOnSuccess {
                val model = Gson().fromJson(it, QianShouRenModel::class.java)
                if (!TextUtils.isEmpty(model.userId)) {
                    qianshouId = model.userId
                    icon.set(model.userIcon)
                    name.set(model.nickname)
                    flag = "2"
                }
            }
    }


}