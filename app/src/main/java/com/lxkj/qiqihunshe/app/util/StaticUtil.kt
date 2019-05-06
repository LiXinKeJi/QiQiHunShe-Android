package com.lxkj.qiqihunshe.app.util

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Environment
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.exception.Utils
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.ui.dialog.YesOrNoDialog
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import io.reactivex.Single
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/20
 */
object StaticUtil {


    val APKPath = Environment.getExternalStorageDirectory().path + "/Download/Earn.apk"


    var uid = ""//用户id
    var sex = ""//用户性别  0女 1男
    var headerUrl = ""//用户自己的头像
    var nickName = ""//昵称
    var age = ""

    var fill = ""// 0未完善资料 1已完善资料

    var lat = ""// 当前纬度
    var lng = ""//当前经度
    var address = "" //当前位置

    var amount = ""//账户总额

    var rytoken = ""//融云token

    val Beecloud_Appid = "b66edf2d-f7c3-480f-83de-0339007bded0"
    val Beecloud_AppSecret = "17dc426a-120b-47b9-a788-ddeb094542b4"
    val Weixin_Appid = "wx56f7adc719a7d5cd"
    val Weixin_AppSecret = "ecdf8ad6bd9d50d9575b6918a31f00a1"


    var isReal = ""//是否实名认证,  0未认证 1待审核 2已认证 3认证失败

    var bail = ""//信誉金 0代表未缴纳
    var foul = ""//违规次数
    var marriage = ""// 情感状态 0未婚 1已婚 2离异


    //获取平台信誉金
    @SuppressLint("CheckResult")
    fun getReputationMoney(context: Activity) {
        val json = "{\"cmd\":\"getBail\"" + "}"
        Utils.retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val price = obj.getString("price")
                val json =
                    "{\"cmd\":\"addBailOrder\",\"uid\":\"" + StaticUtil.uid + "\",\"price\":\"" + price + "\"}"
                Utils.retrofit.getData(json).async()
                    .compose(SingleCompose.compose(object : SingleObserverInterface {
                        override fun onSuccess(response: String) {
                            val obj = JSONObject(response)
                            val bundle = Bundle()
                            bundle.putDouble("money", price.toDouble())
                            bundle.putString("num", obj.getString("orderId"))
                            bundle.putInt("flag", 0)
                            MyApplication.openActivityForResult(context, PayActivity::class.java, bundle, 0)
                        }
                    }, context)).subscribe({}, { dispatchFailure(context, it) })
            }
        }, context)).subscribe({}, { dispatchFailure(context, it) })
    }


    fun isBail(context: Activity): Boolean {//聊天里发起约见，和报名及发布报名必须完成缴纳信誉金才可，否则弹缴纳窗口
        if (bail == "0") {
            YesOrNoDialog.showDialog(context, "缴纳信誉金", "以后再说", "缴纳", object : YesOrNoDialog.YesOrNoCallback {
                @SuppressLint("CheckResult")
                override fun YesOrNo(b: Boolean) {
                    if (b) {
                        getReputationMoney(context)
                    }
                }
            })
            return true
        }
        return false
    }


    //是否实名认证，不能发送消息，发布动态、邀约、才艺、评论、打赏
    fun isRealNameAuth(activity: Activity): Boolean {
        if (isReal != "2") {
            ToastUtil.showTopSnackBar(activity, "请先实名认证")
            return false
        }
        return true
    }


    //已婚，不可以聊天，并且不可以参加附近邀约
    fun isMarriage(activity: Activity): Boolean {
        if (isReal != "2") {
            ToastUtil.showTopSnackBar(activity, "已婚状态不能会话")
            return false
        }
        return true
    }

}