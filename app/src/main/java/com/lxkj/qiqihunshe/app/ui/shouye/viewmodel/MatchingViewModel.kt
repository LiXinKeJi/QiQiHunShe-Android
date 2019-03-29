package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil

/**
 * 匹配
 * Created by Slingge on 2019/2/26
 */
class MatchingViewModel : BaseViewModel() {

    var type = "1"
    /**
     * 1聊 2语 人物匹配
     */
    fun randomUser() {
        var params = HashMap<String, String>()
        params["cmd"] = "randomUser"
        params["uid"] = StaticUtil.uid
        params["type"] = type
        retrofit.getData(Gson().toJson(params))
            .async().doOnSuccess {
                val model = Gson().fromJson(it, ShouYeModel::class.java)
                if (model.result == "0"){
                    ToastUtil.showTopSnackBar(activity, "匹配成功！" + model.userId)
                }else{
                    ToastUtil.showTopSnackBar(activity, model.resultNote)
                }
            }.subscribe()
    }

}