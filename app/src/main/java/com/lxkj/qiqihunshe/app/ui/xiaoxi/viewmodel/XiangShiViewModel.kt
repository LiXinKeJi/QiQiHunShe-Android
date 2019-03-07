package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FraXiangshiBinding

/**
 * Created by Slingge on 2019/2/28
 */
class XiangShiViewModel : BaseViewModel() {


    var bind: FraXiangshiBinding? = null

    //获取好友
    fun getNewMsg(){
        var params = HashMap<String,String>()
        params["cmd"] = "newMsg"
        params["uid"] = StaticUtil.uid
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, XxModel::class.java)
                    for(i in 0 until model.dataList.size){
                        when (model.dataList[i].type){
                            "1" -> { //1互动通知 2小七提醒
                                bind?.tvMessage?.text = model.dataList[i].content
                                if (model.dataList[i].count == "0")
                                    bind?.tvNotificationNum?.visibility = View.GONE
                                else
                                    bind?.tvNotificationNum?.visibility = View.VISIBLE
                            }
                            "2" -> {
                                bind?.tvHintMessage?.text = model.dataList[i].content
                                if (model.dataList[i].count == "0")
                                    bind?.tvHintMessageNum?.visibility = View.GONE
                                else
                                    bind?.tvHintMessageNum?.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {

            })
    }




}