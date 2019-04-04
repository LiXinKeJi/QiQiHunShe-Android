package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import com.baidu.mapapi.search.core.PoiInfo
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.rongrun.model.YueJianModel
import com.lxkj.qiqihunshe.app.rongrun.message.*
import com.lxkj.qiqihunshe.app.rongrun.model.ImpressionScoreModel
import com.lxkj.qiqihunshe.app.rongrun.model.QiQiAssistModel
import com.lxkj.qiqihunshe.app.rongrun.model.ShiYueModel
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog2
import com.lxkj.qiqihunshe.app.ui.model.JuBaoModel
import com.lxkj.qiqihunshe.app.ui.dialog.DatePop
import com.lxkj.qiqihunshe.app.ui.fujin.model.DivideModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.RelationsMeModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityChatDetailsBinding
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Slingge on 2019/3/12
 */
class ChatViewModel : BaseViewModel(), DatePop.DateCallBack, UpLoadFileCallBack {

    val JuBaoList by lazy { ArrayList<String>() }

    val jubaoFilePath by lazy { ArrayList<String>() }
    private val upFileUtil by lazy { UpFileUtil(activity!!, this) }


    var targetId = ""//对方id
    var title = ""//标题，对方昵称

    lateinit var bind: ActivityChatDetailsBinding

    val datePop by lazy { DatePop(activity, this) }

    var info: PoiInfo? = null

    var isAppointment = false//是否和别人约见


    fun getJuBaoConten(): Single<String> {
        val json = "{\"cmd\":\"getReportList\",\"type\":\"" + "1" + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, JuBaoModel::class.java)
                    JuBaoList.addAll(model.dataList)
                    showReportDialog()
                }
            }, activity))
    }


    private var JuBaoContent = ""//举报内容
    fun showReportDialog() {//聊天举报
        ReportDialog2.show(activity!!, JuBaoList, object : ReportDialog2.ReportContentCallBack {
            override fun report(content: String) {
                JuBaoContent = content
                if (jubaoFilePath.isNotEmpty()) {
                    upFileUtil.setListPath(jubaoFilePath)
                } else {
                    chatJuBao("")
                }
            }
        })
    }


    override fun uoLoad(url: String) {

    }

    override fun uoLoad(url: List<String>) {
        val sb = StringBuffer()
        for (file in url) {
            sb.append("$url|")
        }
        chatJuBao(sb.toString().substring(0, sb.toString().length - 1))
    }


    @SuppressLint("CheckResult")
    fun chatJuBao(file: String) {
        val json =
            "{\"cmd\":\"liaotianjubao\",\"uid\":\"" + StaticUtil.uid + "\",\"taid\":\"" + targetId +
                    "\",\"content\":\"" + JuBaoContent + "\",\"images\":\"" + file + "\"}"
        retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(activity, "举报成功")
            }
        }, activity)).subscribe({}, { toastFailure(it) })
    }


    fun sendMessage1() {
        val shopMessage = CustomizeMessage1()
        shopMessage.reject = "0"
        shopMessage.content = "约您见面，是否答应？"
        RongYunUtil.sendMessage1(targetId, shopMessage, "")
    }

    fun sendMessage2(type: String) {
        val shopMessage = CustomizeMessage2()
        shopMessage.type = type
        shopMessage.content = title + "拒绝当前请求"
        shopMessage.price = "0.0"
        RongYunUtil.sendMessage2(targetId, shopMessage, "")
    }

    fun sendMessage2(type: String, price: String) {
        val shopMessage = CustomizeMessage2()
        shopMessage.type = type
        shopMessage.content = title + "拒绝当前请求"
        shopMessage.price = price
        RongYunUtil.sendMessage2(targetId, shopMessage, "")
    }

    fun sendMessage3() {
        val shopMessage = CustomizeMessage3()
        RongYunUtil.sendMessage3(targetId, shopMessage, "")
    }


    fun selectTime() {
        if (!datePop.isShowing) {
            datePop.showAtLocation(bind.clMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    private var dateTime = ""
    override fun position(
        position1: String, position2: String, position3: String,
        position4: String, position5: String, position6: String
    ) {
        dateTime = "$position1-$position2-$position3 $position4:$position5:$position6"
    }

    override fun position() {
        info?.let {
            val shopMessage = CustomizeMessage4()
            shopMessage.content = it.name
            shopMessage.address = it.address
            shopMessage.lat = it.location.latitude.toString()
            shopMessage.lon = it.location.longitude.toString()
            shopMessage.time = dateTime
            RongYunUtil.sendMessage4(targetId, shopMessage, "")
        }
    }


    //两人约见
    fun yueJian(model: YueJianModel): Single<String> {
        abLog.e("约见", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val obj = JSONObject(response)
                    val shopMessage = CustomizeMessage5()
                    shopMessage.address = model.address
                    shopMessage.lat = model.lat
                    shopMessage.lon = model.lon
                    shopMessage.time = model.arrivaltime
                    shopMessage.yuejianId = obj.getString("yuejianId")
                    RongYunUtil.sendMessage5(targetId, shopMessage, "")

                    val shopMessage6 = CustomizeMessage6()
                    shopMessage6.address = model.address
                    shopMessage6.lat = model.lat
                    shopMessage6.lon = model.lon
                    shopMessage6.yuejianId = obj.getString("yuejianId")
                    RongYunUtil.sendMessage6(targetId, shopMessage6, "")
                }
            }, activity))
    }


    fun shiYue(model: ShiYueModel): Single<String> {
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "提交成功")
                }
            }, activity))
    }


    //七七协助
    fun addHelp(model: QiQiAssistModel): Single<String> {
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "提交成功")
                }
            }, activity))
    }

    //解除关系
    fun jiechu(): Single<String> {
        val json = "{\"cmd\":\"relieverelationship\",\"uid\":\"" + StaticUtil.uid + "\",\"tauid\":\"" + targetId + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    activity?.let {
                        ToastUtil.showToast("解除成功")
                        it.finish()
                    }
                }
            }, activity))
    }

    //印象评分，解除关系，完成约见
    @SuppressLint("CheckResult")
    fun dianping(model: ImpressionScoreModel): Single<String> {
        abLog.e("解除关系", Gson().toJson(model))
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    if (model.type == "1") {//1解除关系 2完成约见
                        activity?.let {
                            ToastUtil.showToast("解除成功")
                            it.finish()
                        }
                    } else {
                        ToastUtil.showTopSnackBar(activity, "评分成功")
                    }
                }
            }, activity))
    }


    //消费划分
    fun huafen(model: DivideModel): Single<String> {
        return retrofit.getData(Gson().toJson(model)).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val shopMessage7 = CustomizeMessage7()
                    shopMessage7.price = model.money
                    shopMessage7.yuejianId = model.yuejianId
                    RongYunUtil.sendMessage7(targetId, shopMessage7, "")
                }
            }, activity))
    }


    //是否是好友
    fun isXiangShi() {

        if (RongYunUtil.isLinShiModel == 0) {// 0否 1是
            bind.group.visibility = View.VISIBLE
        } else {
            bind.group.visibility = View.GONE
        }
    }


    //同意or拒绝，进入相识模式
    fun argee(type: String): Single<String> {//0同意 1拒绝
        val json =
            "{\"cmd\":\"agreerelationship\",\"uid\":\"" + StaticUtil.uid + "\",\"tauid\":\"" + targetId +
                    "\",\"type\":\"" + type + "\"}"
        abLog.e("进入相识模式", json)
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    if (type == "0") {
                        RongYunUtil.isLinShiModel = 1
                        ToastUtil.showTopSnackBar(activity, "已同意邀请")
                        EventBus.getDefault().post(EventCmdModel("xiangshi",""))
                    } else {
                        ToastUtil.showTopSnackBar(activity, "已拒绝邀请")
                    }
                    bind.group.visibility = View.GONE
                }
            }, activity))
    }


    //获取约见详情，判断消息发送，避免重复
    fun yuejianDetails(): Single<String> {
        val json =
            "{\"cmd\":\"yuejianDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"taid\":\"" + targetId +
                    "\",\"yuejianId\":\"" + "" + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {

            }
        }, activity))
    }


    //和别人的关系，是否已经在和别人邀约
    fun RelationsMe(): Single<String> {
        val json = "{\"cmd\":\"getUserChatList\",\"uid\":\"" + StaticUtil.uid + "\",\"type\":\"" + "1" + "\"}"
        return retrofit.getData(json).async()
            .doOnSuccess {
                val model = Gson().fromJson(it, RelationsMeModel::class.java)
                abLog.e("和别人的关旭", it)
                if (model.dataList.isNotEmpty()) {
                    isAppointment = true
                }
            }
    }

}