package com.lxkj.qiqihunshe.app.rongrun

import android.content.Context
import android.text.TextUtils
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil

/**
 * 记录消息id，设置消息状态
 * Created by Slingge on 2019/5/8
 */
object MessageIdUtil {

    var MsgId1 = ""
    var MsgId3 = ""
    var MsgId4 = ""
    var MsgId5 = ""
    var MsgId6 = ""
    var MsgId7 = ""

    fun getMsg1Id(context: Context): String {
        if (TextUtils.isEmpty(MsgId1)) {
            MsgId1 = SharedPreferencesUtil.getSharePreStr(context, "msg1")
        }
        return MsgId1
    }


    fun getMsg3Id(context: Context): String {
        if (TextUtils.isEmpty(MsgId3)) {
            MsgId3 = SharedPreferencesUtil.getSharePreStr(context, "msg3")
        }
        return MsgId3
    }

    fun getMsg4Id(context: Context): String {
        if (TextUtils.isEmpty(MsgId4)) {
            MsgId4 = SharedPreferencesUtil.getSharePreStr(context, "msg4")
        }
        return MsgId4
    }

    fun getMsg5Id(context: Context): String {
        if (TextUtils.isEmpty(MsgId5)) {
            MsgId5 = SharedPreferencesUtil.getSharePreStr(context, "msg5")
        }
        return MsgId5
    }

    fun getMsg6Id(context: Context): String {
        if (TextUtils.isEmpty(MsgId6)) {
            MsgId6 = SharedPreferencesUtil.getSharePreStr(context, "msg6")
        }
        return MsgId6
    }

    fun getMsg7Id(context: Context): String {
        if (TextUtils.isEmpty(MsgId7)) {
            MsgId7 = SharedPreferencesUtil.getSharePreStr(context, "msg7")
        }
        return MsgId7
    }


    fun saveMsg1(context: Context, msgId: String) {
        SharedPreferencesUtil.putSharePre(context, "msg1", msgId)
    }

    fun saveMsg3(context: Context, msgId: String) {
        SharedPreferencesUtil.putSharePre(context, "msg3", msgId)
    }

    fun saveMsg4(context: Context, msgId: String) {
        SharedPreferencesUtil.putSharePre(context, "msg4", msgId)
    }


    fun saveMsg5(context: Context, msgId: String) {
        SharedPreferencesUtil.putSharePre(context, "msg5", msgId)
    }


    fun saveMsg6(context: Context, msgId: String) {
        SharedPreferencesUtil.putSharePre(context, "msg6", msgId)
    }

    fun saveMsg7(context: Context, msgId: String) {
        SharedPreferencesUtil.putSharePre(context, "msg7", msgId)
    }


}