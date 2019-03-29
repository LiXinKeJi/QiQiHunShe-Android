package com.lxkj.qiqihunshe.app.rongrun

import android.app.Activity
import android.text.TextUtils
import com.lxkj.qiqihunshe.app.rongrun.message.*
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imkit.RongIM
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.CSCustomServiceInfo
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message

/**
 * Created by Slingge on 2019/3/12
 */
object RongYunUtil {


    //连接融云服务器
    fun initService() {
        abLog.e("融云token", StaticUtil.rytoken)
        RongIM.connect(StaticUtil.rytoken, object : RongIMClient.ConnectCallback() {
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


    //去客服
    fun toService(activity: Activity?) {
        if (TextUtils.isEmpty(StaticUtil.rytoken)) {
            ToastUtil.showTopSnackBar(activity, "IM初始化错误")
            return
        }
        val csBuilder = CSCustomServiceInfo.Builder()
        val csInfo = csBuilder.nickName("融云").build()
        RongIM.getInstance().startCustomerServiceChat(activity, "客服id", "在线客服", csInfo)
    }


    /**
     * 发送自定义信息
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

    fun sendMessage2(id: String, message: CustomizeMessage2, pushContent: String) {
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

    fun sendMessage3(targetId: String, shopMessage: CustomizeMessage3, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
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

    fun sendMessage4(targetId: String, shopMessage: CustomizeMessage4, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
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

    //开始约见
    fun sendMessage5(targetId: String, shopMessage: CustomizeMessage5, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
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

    fun sendMessage6(targetId: String, shopMessage: CustomizeMessage6, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
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

    fun sendMessage7(targetId: String, shopMessage7: CustomizeMessage7, s: String) {
        RongIM.getInstance()
            .sendMessage(
                Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage7),
                s,
                null,
                object :
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