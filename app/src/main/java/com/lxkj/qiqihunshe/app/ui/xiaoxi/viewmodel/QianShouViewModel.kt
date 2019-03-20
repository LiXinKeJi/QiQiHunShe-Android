package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.databinding.ObservableField
import com.google.gson.JsonObject
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FragmentQianshouBinding
import io.reactivex.Single
import org.json.JSONObject

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
    }

    fun getQianShou(): Single<String> {
        val json = "{\"cmd\":\"getQianshou\",\"uid\":\"" + StaticUtil.uid + "\"}"

        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val obj = JSONObject(response)
                    qianshouId = obj.getString("userId")
                    icon.set(obj.getString("userIcon"))
                    name.set(obj.getString("nickname"))
                    flag = "2"
                }
            }, fragment!!.activity))
    }


}