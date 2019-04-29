package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.MessageAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.util.RecyclerItemTouchListener
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import java.util.ArrayList

/**
 * Created by Slingge on 2019/3/1
 */
class YueHuiVieModel : BaseViewModel() {

    var bind: ActivityRecyvlerviewBinding? = null
    val friendUserList by lazy { java.util.ArrayList<FindUserRelationshipModel.dataModel>() }

    val messageAdapter by lazy { MessageAdapter(fragment!!.activity!!) }

    fun initViewmodel() {
        bind!!.recycler.isFocusable = false
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = messageAdapter

        bind!!.recycler.addOnItemTouchListener(object : RecyclerItemTouchListener(bind!!.recycler) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0 || i >= friendUserList.size) {
                    return
                }
                RongYunUtil.toChat(fragment!!.activity!!, friendUserList[i].userId, friendUserList[i].realname, 2)
            }
        })

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
        RongIM.getInstance().removeConversation(
            Conversation.ConversationType.PRIVATE, tauid,
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


    fun isFriend(): Single<String> {
        val json = "{\"cmd\":\"getUserChatList\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "2" + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(it: String) {
                    val model = Gson().fromJson(it, FindUserRelationshipModel::class.java)
                    abLog.e("我的好友关系-约会", it)
                    friendUserList.addAll(model.dataList)
                    getAllIMList()
                }
            }, fragment!!.activity))
    }



    //本地回话列表中的id集合
    fun getAllIMList() {
        abLog.e("获取本地好友", "")
        RongIM.getInstance().getConversationList(object : RongIMClient.ResultCallback<List<Conversation>>() {
            override fun onSuccess(p0: List<Conversation>?) {
                val chatList by lazy { ArrayList<Conversation>() }
                if (p0 == null) {
                    messageAdapter.loadMore(friendUserList, 1)
                    return
                }
                chatList.clear()
                chatList.addAll(p0)
                abLog.e("会话列表", Gson().toJson(p0))

                if (chatList.isNotEmpty()) {
                    for (msg in chatList) {
                        for (i in 0 until friendUserList.size) {
                            if (msg.targetId != friendUserList[i].userId) {
                                continue
                            }
                            if (msg.unreadMessageCount > 0) {
                                friendUserList[i].isNewMsg = msg.unreadMessageCount
                                if( msg.latestMessage.mentionedInfo!=null){
                                    friendUserList[i].content = msg.latestMessage.mentionedInfo.mentionedContent
                                }
                            } else {
                                friendUserList[i].isNewMsg = -1
                            }
                        }
                    }
                }
                messageAdapter.loadMore(friendUserList, 1)
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "获取会话列表错误 ${p0?.message}")
            }
        })

    }


}