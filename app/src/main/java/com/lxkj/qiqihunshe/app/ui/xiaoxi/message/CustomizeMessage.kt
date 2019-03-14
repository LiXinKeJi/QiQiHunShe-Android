package com.lxkj.qiqihunshe.app.ui.xiaoxi.message

import android.annotation.SuppressLint
import android.os.Parcel
import android.util.Log
import io.rong.imlib.model.MessageContent
import org.json.JSONException
import org.json.JSONObject

import java.io.UnsupportedEncodingException

/**
 * 注解名：MessageTag ；属性：value ，flag； value 即 ObjectName 是消息的唯一标识不可以重复，
 * 开发者命名时不能以 RC 开头，避免和融云内置消息冲突；flag 是用来定义消息的可操作状态。
 * 如下面代码段，自定义消息名称 CustomizeMessage ，vaule 是 app:custom ，
 * flag 是 MessageTag.ISCOUNTED | MessageTag.ISPERSISTED 表示消息计数且存库。
 * app:RedPkgMsg: 这是自定义消息类型的名称，测试的时候用"app:RedPkgMsg"；
 * Created by Slingge on 2019/3/14
 */
/*@MessageTag(value = "app:RedPkgMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)*/
@SuppressLint("ParcelCreator")
class CustomizeMessage : MessageContent() {

    //自定义的属性
    private val title: String? = null
    private val storeName: String? = null
    private val desc1: String? = null
    private val desc2: String? = null


    override fun encode(): ByteArray? {
        val jsonObj = JSONObject()

        try {
            jsonObj.put("title", this.title)
            jsonObj.put("storeName", this.storeName)
            jsonObj.put("desc1", this.desc1)
            jsonObj.put("desc2", this.desc2)

        } catch (e: JSONException) {
            Log.e("JSONException", e.message)
        }

        try {
            return jsonObj.toString().toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        return null
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }






}
