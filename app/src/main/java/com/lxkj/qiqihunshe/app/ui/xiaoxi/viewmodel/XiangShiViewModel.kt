package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.MessageAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FraXiangshiBinding
import io.reactivex.Single
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/28
 */
class XiangShiViewModel : BaseViewModel() {


    var bind: FraXiangshiBinding? = null

    val friendUserList by lazy { ArrayList<FindUserRelationshipModel.dataModel>() }

    val messageAdapter by lazy { MessageAdapter(fragment!!.activity as Activity) }


    fun init() {
        bind?.let {
            it.recycler.layoutManager = LinearLayoutManager(fragment!!.activity)
            it.recycler.adapter = messageAdapter

            messageAdapter.flag = 1

            val userList = ArrayList<FindUserRelationshipModel.dataModel>()
            for (user in friendUserList) {
                if (user.relationship == "0" || user.relationship == "1"|| user.relationship == "-1") {//-1新消息
                    userList.add(user)
                }
            }

            abLog.e("相识列表",Gson().toJson(userList))
            messageAdapter.upData(userList)

        }

    }


    fun getNewMsg(): Single<String> {
        var params = HashMap<String, String>()
        params["cmd"] = "newMsg"
        params["uid"] = StaticUtil.uid
        return retrofit.getData(Gson().toJson(params))
            .async()
            .doOnSuccess {
                val model = Gson().fromJson(it, XxModel::class.java)
                for (i in 0 until model.dataList.size) {
                    when (model.dataList[i].type) {
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
    }


    //解除关系
    fun jiechu(tauid: String, position: Int): Single<String> {
        val json = "{\"cmd\":\"relieverelationship\",\"uid\":\"" + StaticUtil.uid + "\",\"tauid\":\"" + tauid + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    removeCall(tauid, position)
                }
            }, fragment!!.activity))
    }


    //删除会话
    fun removeCall(tauid: String, position: Int) {
        RongIM.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, tauid,
            object : RongIMClient.ResultCallback<Boolean>() {
                override fun onError(p0: RongIMClient.ErrorCode?) {
                    ToastUtil.showTopSnackBar(fragment!!.activity, p0?.message)
                }

                override fun onSuccess(p0: Boolean?) {
                    messageAdapter.removeItem(position)
                }

            }
        )
    }


    //阅读互动通知
    fun redMsg(): Single<String> {
        val json = "{\"cmd\":\"readMsg\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async().doOnSuccess { }
    }


}




