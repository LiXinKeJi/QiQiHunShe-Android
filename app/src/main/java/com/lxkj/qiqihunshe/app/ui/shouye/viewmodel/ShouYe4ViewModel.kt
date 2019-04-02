package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.activity.MatchingActivity
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.VoiceTimeModel
import com.lxkj.qiqihunshe.app.util.GetDateTimeUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/26
 */
class ShouYe4ViewModel : BaseViewModel() {


    var answer = "0"//0未回答 1已回答


    fun getPeiList(): Single<String> {
        var params = HashMap<String, String>()
        params["cmd"] = "peiList"
        params["uid"] = StaticUtil.uid
        return retrofit.getData(Gson().toJson(params))
            .async()
            .doOnSuccess {
                val model = Gson().fromJson(it, ShouYeModel::class.java)
                answer = model.answer;
            }
    }


    //语音匹配时间
    @SuppressLint("CheckResult")
    fun matchingTime(): Single<String> {
        val json = "{\"cmd\":\"voiceTime\"" + "}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
            }
        }, fragment!!.activity))
    }


}