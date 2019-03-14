package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog2
import com.lxkj.qiqihunshe.app.ui.model.JuBaoModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.ChatReportModel
import io.reactivex.Single

/**
 * Created by Slingge on 2019/3/12
 */
class ChatViewModel : BaseViewModel() {

    val JuBaoList by lazy { ArrayList<String>() }

    val reportModel by lazy { ChatReportModel() }

    fun jubao() {}


    fun getJuBaoConten(): Single<String> {
        val json = "{\"cmd\":\"getReportList\",\"type\":\"" + "1" + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, JuBaoModel::class.java)
                    JuBaoList.addAll(model.dataList)
                    ReportDialog2.show(activity!!, JuBaoList)
                }
            }, activity))
    }

}