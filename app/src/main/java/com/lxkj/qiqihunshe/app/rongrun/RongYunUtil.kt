package com.lxkj.qiqihunshe.app.rongrun

import android.app.Activity
import android.net.Uri
import android.text.TextUtils
import com.lxkj.qiqihunshe.app.rongrun.message.*
import com.lxkj.qiqihunshe.app.rongrun.plugin.MyExtensionEmptyModule
import com.lxkj.qiqihunshe.app.rongrun.plugin.MyExtensionModule
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.IExtensionModule
import io.rong.imkit.RongExtensionManager
import io.rong.imkit.RongIM
import io.rong.imkit.plugin.IPluginModule
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.*
import io.rong.message.TextMessage


/**
 * Created by Slingge on 2019/3/12
 */
object RongYunUtil {

    val serviceId = "1"//客服id

    var isLinShiModel = -2//0临时消息 1:相识，2:约会,3:牵手,4:拉黑，5通讯

    val PluginList by lazy { ArrayList<IPluginModule>() }

    //连接融云服务器
    fun initService() {
        abLog.e("融云token", StaticUtil.rytoken)
        RongIM.connect(StaticUtil.rytoken, object : RongIMClient.ConnectCallback() {
            override fun onSuccess(p0: String?) {
//                ToastUtil.showToast("已连接融云")
                RongIM.getInstance()
                    .setCurrentUserInfo(UserInfo(StaticUtil.uid, StaticUtil.nickName, Uri.parse(StaticUtil.headerUrl)))
            }

            override fun onError(p0: RongIMClient.ErrorCode) {
                abLog.e("连接融云失败", p0.value.toString())
//                ToastUtil.showToast(p0.message)
            }

            override fun onTokenIncorrect() {
            }
        })
    }


    fun toChat(activity: Activity, userId: String, nickname: String) {
        if (!StaticUtil.isRealNameAuth(activity)) {
            return
        }
        if (!StaticUtil.isMarriage(activity)) {
            return
        }
        isLinShiModel = -2
        RongExtensionManager.getInstance().registerExtensionModule(MyExtensionEmptyModule())
        RongIM.getInstance().startPrivateChat(
            activity, userId, nickname
        )
    }

    fun toChat(activity: Activity, userId: String, nickname: String, isLinShiModel: Int) {
        if (!StaticUtil.isRealNameAuth(activity)) {
            return
        }

        val moduleList = RongExtensionManager.getInstance().extensionModules
        var defaultModule: IExtensionModule? = null
        for (module in moduleList) {
            if (module is DefaultExtensionModule) {
                defaultModule = module
                break
            }
        }
        if (moduleList != null) {
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule)
            }
        }
        if (isLinShiModel == 0) {
            RongExtensionManager.getInstance()
                .registerExtensionModule(MyExtensionEmptyModule())//临时模式，不显示自定义区域
        } else {
            RongExtensionManager.getInstance()
                .registerExtensionModule(MyExtensionModule(1))//临时模式，不显示自定义区域
        }

        this.isLinShiModel = isLinShiModel
        RongIM.getInstance().startPrivateChat(
            activity, userId, nickname
        )
    }


    //去客服
    fun toService(activity: Activity?) {
        if (TextUtils.isEmpty(StaticUtil.rytoken)) {
            ToastUtil.showTopSnackBar(activity, "IM初始化错误")
            return
        }
        val csBuilder = CSCustomServiceInfo.Builder()
        val csInfo = csBuilder.nickName("融云").build()
        RongIM.getInstance().startCustomerServiceChat(activity, serviceId, "在线客服", csInfo)
    }


    fun sendWordsMessage(id: String, msg: String) {
        val myTextMessage = TextMessage.obtain(msg)
        val myMessage = Message.obtain(id, Conversation.ConversationType.PRIVATE, myTextMessage)

        RongIM.getInstance().sendMessage(myMessage, null, null, object : IRongCallback.ISendMessageCallback {
            override fun onAttached(p0: Message?) {
            }

            override fun onSuccess(p0: Message?) {
            }

            override fun onError(p0: Message?, p1: RongIMClient.ErrorCode?) {
            }

        })
    }


    /**
     * 发送自定义信息
     *
     * @param id          收信人id
     * @param message     自定义消息
     * @param pushContent push 接受cotent
     */
    fun sendMessage1(activity: Activity, id: String, message: CustomizeMessage1, pushContent: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(id, Conversation.ConversationType.PRIVATE, message), pushContent, null, object :
                IRongCallback.ISendMessageCallback {
                override fun onAttached(message: Message) {
                    abLog.e("sendMessage1", "消息发送")
                    MessageIdUtil.saveMsg1(activity, message.messageId.toString())
                }

                override fun onSuccess(message: Message) {
                    abLog.e("sendMessage1", "消息发送成功")
                }

                override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                    abLog.e("sendMessage1", errorCode.message)
                    sendMsgResult(errorCode.message)
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
                    sendMsgResult(errorCode.message)
                }
            })
    }

    fun sendMessage3(activity: Activity, targetId: String, shopMessage: CustomizeMessage3, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
                IRongCallback.ISendMessageCallback {
                override fun onAttached(message: Message) {
                    abLog.e("sendMessage1", "消息发送")
                }

                override fun onSuccess(message: Message) {
                    abLog.e("sendMessage1", "消息发送成功")
                    MessageIdUtil.saveMsg3(activity, message.messageId.toString())
                    abLog.e("消息3Id", message.messageId.toString() + "////////////" + MessageIdUtil.getMsg3Id(activity))
                }

                override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                    abLog.e("sendMessage1", errorCode.message)
                    sendMsgResult(errorCode.message)
                }
            })
    }

    fun sendMessage4(activity: Activity, targetId: String, shopMessage: CustomizeMessage4, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
                IRongCallback.ISendMessageCallback {
                override fun onAttached(message: Message) {
                    abLog.e("sendMessage1", "消息发送")
                }

                override fun onSuccess(message: Message) {
                    abLog.e("sendMessage1", "消息发送成功")
                    MessageIdUtil.saveMsg4(activity, message.messageId.toString())
                }

                override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                    abLog.e("sendMessage1", errorCode.message)
                    sendMsgResult(errorCode.message)
                }
            })
    }

    //开始约见
    fun sendMessage5(activity: Activity, targetId: String, shopMessage: CustomizeMessage5, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
                IRongCallback.ISendMessageCallback {
                override fun onAttached(message: Message) {
                    abLog.e("sendMessage1", "消息发送")
                }

                override fun onSuccess(message: Message) {
                    abLog.e("sendMessage1", "消息发送成功")
                    MessageIdUtil.saveMsg5(activity, message.messageId.toString())
                }

                override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                    abLog.e("sendMessage1", errorCode.message)
                    sendMsgResult(errorCode.message)
                }
            })
    }

    fun sendMessage6(activity: Activity, targetId: String, shopMessage: CustomizeMessage6, s: String) {
        RongIM.getInstance()
            .sendMessage(Message.obtain(targetId, Conversation.ConversationType.PRIVATE, shopMessage), s, null, object :
                IRongCallback.ISendMessageCallback {
                override fun onAttached(message: Message) {
                    abLog.e("sendMessage6", "消息发送")
                }

                override fun onSuccess(message: Message) {
                    abLog.e("sendMessage6", "消息发送成功")
                    MessageIdUtil.saveMsg6(activity, message.messageId.toString())
                }

                override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                    abLog.e("sendMessage6", errorCode.message)
                    sendMsgResult(errorCode.message)
                }
            })
    }

    fun sendMessage7(activity: Activity, targetId: String, shopMessage7: CustomizeMessage7, s: String) {
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
                        MessageIdUtil.saveMsg7(activity, message.messageId.toString())
                    }

                    override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
                        abLog.e("sendMessage1", errorCode.message)
                        sendMsgResult(errorCode.message)
                    }
                })
    }


    fun sendLocationMessage(shopMessage: Message) {
        RongIM.getInstance()
            .sendLocationMessage(
                shopMessage,
                "我的位置",
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
                        sendMsgResult(errorCode.message)
                    }
                })
    }

    fun sendMsgResult(message: String) {
        when (message) {
            "IPC is not connected" -> ToastUtil.showToast("发送失败：IPC未连接")
            "the parameter is error." -> ToastUtil.showToast("发送失败：参数错误")
            else -> ToastUtil.showToast("发送失败")
        }
    }


    //移出黑名单
    fun removeBlackList(id: String) {
        RongIM.getInstance().removeFromBlacklist(id, object : RongIMClient.OperationCallback() {
            override fun onSuccess() {
                abLog.e("removeBlackList", "移出黑名单成功")
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
                abLog.e("removeBlackList", "移出黑名单失败")
            }

        })
    }


    //设置自定义消息已操作
    fun setMessageStatus(msgId: Int) {
        val status = Message.ReceivedStatus(4)
        status.setDownload()
        RongIM.getInstance().setMessageReceivedStatus(msgId, status)
    }


    //设置自定义消息消费方式划分
    fun setMessageStatusXiaoFei(msgId: Int) {
        val status = Message.ReceivedStatus(8)
        status.setDownload()
        RongIM.getInstance().setMessageReceivedStatus(msgId, status)
    }


    //设置失约
    fun setMessageFlag(msgId: Int,type:Int){//type,101我方失约，102我方失约
        val status = Message.ReceivedStatus(type)
        RongIM.getInstance().setMessageReceivedStatus(msgId, status)
    }

}