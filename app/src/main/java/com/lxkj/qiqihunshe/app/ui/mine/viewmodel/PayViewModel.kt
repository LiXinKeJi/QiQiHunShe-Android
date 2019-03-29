package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import android.os.Handler
import android.util.Log
import cn.beecloud.BCPay
import cn.beecloud.BeeCloud
import cn.beecloud.async.BCCallback
import cn.beecloud.entity.BCPayResult
import cn.beecloud.entity.BCReqParams
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import io.reactivex.Single
import java.util.*

/**
 * Created by Slingge on 2019/3/12
 */
class PayViewModel : BaseViewModel() {


    val bannale = ObservableField<String>()

    var payMoney = 0.0//支付金额
    var num = ""//订单号

    var type = -1//0余额支付，1微信支付，2支付宝支付

    private var toastMsg = ""


    fun initViewModel(){
        BeeCloud.setAppIdAndSecret(StaticUtil.Beecloud_Appid,
            StaticUtil.Beecloud_AppSecret)
        // 如果用到微信支付，在用到微信支付的Activity的onCreate函数里调用以下函数.
        // 第二个参数需要换成你自己的微信AppID.
        val initInfo = BCPay.initWechatPay(activity, StaticUtil.Weixin_Appid)
        if (initInfo != null) {
//            ToastUtil.showToast("微信初始化失败")
        }
    }


    fun balanne(): Single<String> {
        val json = "{\"cmd\":\"payByBalance\",\"uid\":\"" + StaticUtil.uid + "\",\"orderId\":\"" + num + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                Success()
            }
        }, activity))
    }

    fun Success() {
        ToastUtil.showToast("支付成功")
        val intent = Intent()
        activity?.let {
            it.setResult(0, intent)
            it.finish()
        }
    }


    fun Pay() {
        if (type == 1) {// 1 微信支付 ，2支付宝支付
            if (BCPay.isWXAppInstalledAndSupported() && BCPay.isWXPaySupported()) {
                val payParams = BCPay.PayParams()
                payParams.channelType = BCReqParams.BCChannelTypes.WX_APP
                payParams.billTitle = "微信支付"   //订单标题
                payParams.billTotalFee = (payMoney * 100).toInt()   //订单金额(分)
                payParams.billNum = num  //订单流水号
                BCPay.getInstance(activity).reqPaymentAsync(
                    payParams,
                    bcCallback
                )            //支付完成后回调入口
            } else {
                ToastUtil.showTopSnackBar(activity, "您尚未安装微信或者安装的微信版本不支持")
            }
        }else if(type == 2){
            val aliParam = BCPay.PayParams()
            aliParam.channelType = BCReqParams.BCChannelTypes.ALI_APP
            aliParam.billTitle = "支付宝支付"
            aliParam.billTotalFee =  (payMoney * 100).toInt() //订单金额(分)
            aliParam.billNum = num
            BCPay.getInstance(activity).reqPaymentAsync(
                aliParam, bcCallback)
        }
    }


    //支付结果返回入口
    private var bcCallback: BCCallback = BCCallback { bcResult ->
        val bcPayResult = bcResult as BCPayResult
        //此处关闭loading界面
        //根据你自己的需求处理支付结果
        val result = bcPayResult.result
        /*
           注意！
           所有支付渠道建议以服务端的状态金额为准，此处返回的RESULT_SUCCESS仅仅代表手机端支付成功
         */
        val msg = mHandler.obtainMessage()
        //单纯的显示支付结果
        msg.what = 2
        if (result == BCPayResult.RESULT_SUCCESS) {
            msg.what = 1
            toastMsg = "用户支付成功"
        } else if (result == BCPayResult.RESULT_CANCEL) {
            toastMsg = "用户取消支付"
        } else if (result == BCPayResult.RESULT_FAIL) {
            msg.what = 3
            if (bcPayResult.errCode == -12 && type == 2) {
                toastMsg = "您尚未安装支付宝"
            } else {
                toastMsg = "支付失败, 原因: " + bcPayResult.errCode +
                        " # " + bcPayResult.errMsg +
                        " # " + bcPayResult.detailInfo
            }
            /*
              * 你发布的项目中不应该出现如下错误，此处由于支付宝政策原因，
              * 不再提供支付宝支付的测试功能，所以给出提示说明
              */
            if (bcPayResult.errMsg == "PAY_FACTOR_NOT_SET" && bcPayResult.detailInfo.startsWith("支付宝参数")) {
                toastMsg = "支付失败：由于支付宝政策原因，故不再提供支付宝支付的测试功能，给您带来的不便，敬请谅解"
            }

            /*
              * 以下是正常流程，请按需处理失败信息
              */
            Log.e("error", toastMsg)

        } else if (result == BCPayResult.RESULT_UNKNOWN) {
            //可能出现在支付宝8000返回状态
            toastMsg = "订单状态未知"
        } else {
            toastMsg = "invalid return"
        }

        mHandler.sendMessage(msg)
    }


    // Defines a Handler object that's attached to the UI thread.
    // 通过Handler.Callback()可消除内存泄漏警告
    private val mHandler = Handler(Handler.Callback { msg ->
        ToastUtil.showTopSnackBar(activity, toastMsg)
        when (msg.what) {
            1 -> {
                Success()
            }
        }
        true
    })


    /**
     * 获取4位随机数
     */
    fun randomNum(): String {
        val sb = StringBuilder()
        //随机生成6位数  发送到聚合
        val random = Random()
        for (i in 0..5) {
            val a = random.nextInt(10)
            sb.append(a)
        }
        return sb.toString()
    }

}