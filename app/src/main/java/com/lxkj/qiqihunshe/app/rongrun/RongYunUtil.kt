package com.lxkj.qiqihunshe.app.rongrun

import com.lxkj.qiqihunshe.app.rongrun.message.CustomizeMessage1
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imkit.RongIM
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message

/**
 * Created by Slingge on 2019/3/12
 */
object RongYunUtil {


    //连接融云服务器
    fun initService() {
        abLog.e("融云token", StaticUtil.rytoken)
        RongIMClient.connect(StaticUtil.rytoken, object : RongIMClient.ConnectCallback() {
            override fun onSuccess(p0: String?) {
//                ToastUtil.showToast("已连接融云")
            }

            override fun onError(p0: RongIMClient.ErrorCode) {
                abLog.e("连接融云失败", p0.value.toString())
//                ToastUtil.showToast(p0.message)
            }

            override fun onTokenIncorrect() {
            }
        })
    }


    /**
     * 发送自定义店铺信息
     *
     * @param id          收信人id
     * @param message     自定义消息
     * @param pushContent push 接受cotent
     */
    fun sendMessage1(id: String, message: CustomizeMessage1, pushContent: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(id, Conversation.ConversationType.PRIVATE, message), pushContent, null, object :
                IRongCallback.ISendMessageCallback {
                override fun onAttached(message: Message) {
                    abLog.e("sendMessage1", "消息发送")
                }

                override fun onSuccess(message: Message) {
                    abLog.e("sendMessage1", "消息发送成功")
                }

                override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                    abLog.e("sendMessage1", errorCode.message)
                    when (errorCode.message) {
                        "IPC is not connected" -> ToastUtil.showToast("发送失败：IPC未连接")
                        "the parameter is error." -> ToastUtil.showToast("发送失败：参数错误")
                        else -> ToastUtil.showToast(errorCode.message)
                    }
                }
            })
    }


}