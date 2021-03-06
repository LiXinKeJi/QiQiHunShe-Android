package com.lxkj.qiqihunshe.app.ui.fujin.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.FileProvider
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.baidu.mapapi.search.core.PoiInfo
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.rongrun.model.ImpressionScoreModel
import com.lxkj.qiqihunshe.app.rongrun.model.QiQiAssistModel
import com.lxkj.qiqihunshe.app.rongrun.model.ShiYueModel
import com.lxkj.qiqihunshe.app.rongrun.model.YueJianModel
import com.lxkj.qiqihunshe.app.ui.dialog.*
import com.lxkj.qiqihunshe.app.ui.fujin.model.DivideModel
import com.lxkj.qiqihunshe.app.ui.fujin.model.YueJianInfoModel
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.ChatViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.quyu.activity.DdtjActivity
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityChatDetailsBinding
import io.rong.imlib.model.Conversation
import io.rong.message.LocationMessage
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.include_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File

/**
 * 临时模式隐藏个人中心，隐藏约见
 * Created by Slingge on 2019/3/12
 */
class ChatActivity : BaseActivity<ActivityChatDetailsBinding, ChatViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() = ChatViewModel()

    override fun getLayoutId() = R.layout.activity_chat_details

//    private val edittext by lazy { findViewById<RongExtension>(R.id.rc_extension).inputEditText }//对话编辑框


    override fun init() {
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

        EventBus.getDefault().register(this)
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener(this)
        AbStrUtil.setDrawableLeft(this, R.drawable.yh_ziliao, tv_right, 0)

        iv_del.setOnClickListener(this)
        iv_yuejian.setOnClickListener(this)
        iv_jubao.setOnClickListener(this)

        iv_sayHello.setOnClickListener(this)
        tv_agree.setOnClickListener(this)
        tv_jiechu.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            val uri = intent.data
            it.title = uri.getQueryParameter("title")
            it.targetId = uri.getQueryParameter("targetId")
            initTitle(it.title)

            if (it.targetId == RongYunUtil.serviceId) {//客服
                iv_yuejian.visibility = View.GONE
                tv_right.visibility = View.GONE
                iv_jubao.visibility = View.GONE
                tv_tip0.visibility = View.GONE
                iv_del.visibility = View.GONE
                iv_sayHello.visibility = View.GONE
            }

            if (RongYunUtil.isLinShiModel == 5) {//通讯模式
                iv_sayHello.visibility = View.GONE
                iv_yuejian.visibility = View.GONE
            } else if (RongYunUtil.isLinShiModel == 0) {//临时消息
                tv_right.visibility = View.GONE
                iv_yuejian.visibility = View.GONE
                it.getRingYunMsgList()
            }

            it.isXiangShi()
            it.RelationsMe().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            it.getDefaultMsg().bindLifeCycle(this).subscribe({ }, { toastFailure(it) })
            it.yuejianDetails().bindLifeCycle(this).subscribe({}, { toastFailure(it) })//用邀约详情判断自定义消息是否可以点击


            /* when (RongYunUtil.isLinShiModel) {
                 0 -> ToastUtil.showTopSnackBar(this, "临时")
                 1 -> ToastUtil.showTopSnackBar(this, "相识")
                 2 -> ToastUtil.showTopSnackBar(this, "约会")
                 3 -> ToastUtil.showTopSnackBar(this, "牵手")
                 4 -> ToastUtil.showTopSnackBar(this, "拉黑")
                 5 -> ToastUtil.showTopSnackBar(this@ChatActivity, "通讯")
             }*/
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_del -> {
                iv_del.visibility = View.GONE
                tv_tip0.visibility = View.GONE
            }
            R.id.iv_yuejian -> {
                if (!StaticUtil.isBail(this)) {
                    viewModel?.yueJIanDetailsModel?.let {
                        if (TextUtils.isEmpty(it.yuejianId)) {
                            viewModel?.let {
                                it.sendMessage1()
                            }
                        } else {
                            val yuejianModel = YueJianModel()
                            yuejianModel.address = it.address
                            yuejianModel.arrivaltime = it.arrivaltime
                            yuejianModel.lat = it.lat
                            yuejianModel.lon = it.lon
                            yuejianModel.taid = it.taid
                            viewModel?.SendMessage56(yuejianModel, it.yuejianId)
                        }
                    }
                }
            }
            R.id.tv_right -> {
                val bundle = Bundle()
                bundle.putString("userId", viewModel?.targetId)
                MyApplication.openActivity(this, PersonalInfoActivity::class.java, bundle)
            }
            R.id.iv_jubao -> {
                viewModel?.let {
                    if (it.JuBaoList.isEmpty()) {
                        it.getJuBaoConten().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                    } else {
                        it.showReportDialog()
                    }
                }
            }
            R.id.iv_sayHello -> {//回复打招呼，回复临时消息
                viewModel?.let {
                    it.ReplyTemporaryNews().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
            R.id.tv_agree -> {//同意,=进入相识模式
                viewModel?.let {
                    it.argee("0").bindLifeCycle(this).subscribe({
                        tv_right.visibility = View.VISIBLE
                        iv_yuejian.visibility = View.VISIBLE
                    }, { toastFailure(it) })
                }
            }
            R.id.tv_jiechu -> {//拒绝，=拉黑
                viewModel?.let {
                    it.argee("1").bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }

        viewModel?.let {
            it.yuejianDetails().bindLifeCycle(this).subscribe({}, { toastFailure(it) })//用邀约详情判断自定义消息是否可以点击
        }
    }

    @Subscribe
    fun onEvent(model: EventCmdModel) {
        when (model.cmd) {
            "1" -> {//拒绝约见
                viewModel?.sendMessage2("1")
            }
            "2" -> {//同意约见
                viewModel?.sendMessage3()
            }
            "3" -> {//选择约见地点
                viewModel?.yueJIanDetailsModel?.let {
                    if (TextUtils.isEmpty(it.address)) {
                        val bundle = Bundle()
                        bundle.putInt("flag", 0)
                        MyApplication.openActivityForResult(this, DdtjActivity::class.java, bundle, 1)
                    }
                }
            }
            "4" -> {//拒绝定位
                viewModel?.let {
                    if (!TextUtils.isEmpty(it.yueJIanDetailsModel.address)) {
                        viewModel?.sendMessage2("2")
                    }
                }
            }
            "5" -> {//小七协助
                QiQiAssistDialog.show(this, object : QiQiAssistDialog.QiQiAssistCallBack {
                    override fun Assist(model: QiQiAssistModel) {
                        viewModel!!.addHelp(model).bindLifeCycle(this@ChatActivity).subscribe({}, { toastFailure(it) })
                    }
                })
            }
            "6" -> {//导航
                viewModel?.yueJIanDetailsModel?.let {
                    if (!TextUtils.isEmpty(it.status)) {//状态 1我方失约 2对方失约 可为空
                        return
                    }
                }
                if (!isInstalled("com.baidu.BaiduMap")) {
                    ToastUtil.showTopSnackBar(this, "请先安装百度地图客户端")
                    return
                }
                val intent = Intent()
                intent.data = Uri.parse(
                    "baidumap://map/direction?destination=latlng:"
                            + model.lat + ","
                            + model.lon + "|name:目的地" + // 终点
                            "&mode=driving" + // 导航路线方式
                            "&src=" + packageName
                )
                startActivity(intent) // 启动调用
            }
            "7" -> {//完成预约

                ImpressionScoreDialog.show(this, object : ImpressionScoreDialog.ImpressionScoreCallBack {
                    override fun Score(models: ImpressionScoreModel) {
                        viewModel?.let {
                            models.taid = it.targetId
                            models.yuejianId = model.res
                            it.dianping(models).bindLifeCycle(this@ChatActivity).subscribe({}, { toastFailure(it) })
                        }
                    }
                })
            }
            "8" -> {//解除关系
                viewModel!!.jiechu().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
            "9" -> {//消费划分
                viewModel?.yueJIanDetailsModel?.let {
                    if (TextUtils.isEmpty(it.paystatus) || it.paystatus != "1") {//支付状态 0未支付 1已支付 2拒绝
                        viewModel?.Messageid = model.lat.toInt()
                        DivideDialog.show(this, model.res)
                    }
                }
            }
            "10" -> {//同意消费划分
                viewModel?.yueJIanDetailsModel?.let {
                    if (TextUtils.isEmpty(it.paystatus) || it.paystatus != "1") {//支付状态 0未支付 1已支付 2拒绝
                        viewModel!!.payYuejianOrder(model.lat, model.res).bindLifeCycle(this)
                            .subscribe({}, { toastFailure(it) })
                    }
                }
            }
            "11" -> {//拒绝消费划分
                viewModel?.yueJIanDetailsModel?.let {
                    if (TextUtils.isEmpty(it.paystatus) || it.paystatus == "0") {//支付状态 0未支付 1已支付 2拒绝
                        viewModel?.sendMessage2("5", model.res)
                    }
                }
            }
        }

        viewModel?.let {
            it.yuejianDetails().bindLifeCycle(this).subscribe({}, { toastFailure(it) })//用邀约详情判断自定义消息是否可以点击
        }
    }

    @Subscribe
    fun onEvent(model: YueJianModel) {//同意约见地点
        viewModel?.let {
            if (TextUtils.isEmpty(it.yueJIanDetailsModel.address)) {
                model.taid = it.targetId
                it.yueJian(model).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }

        viewModel?.let {
            it.yuejianDetails().bindLifeCycle(this).subscribe({}, { toastFailure(it) })//用邀约详情判断自定义消息是否可以点击
        }
    }

    @Subscribe
    fun onEvent(model: ShiYueModel) {//某方失约,type1我方失约 2对方失约

        viewModel?.let {
            it.yuejianDetails().bindLifeCycle(this).subscribe({
                val mode = Gson().fromJson(it, YueJianInfoModel::class.java)

                if (TextUtils.isEmpty(mode.status)) {// 状态 1我方失约 2对方失约 可为空
                    viewModel!!.shiYue(model).bindLifeCycle(this).subscribe({

                        if (model.type == "1") {//type1我方失约 2对方失约
                            RongYunUtil.setMessageFlag(model.messageId, 101)
                        } else {
                            RongYunUtil.setMessageFlag(model.messageId, 102)
                        }
                    }, { toastFailure(it) })

                    return@subscribe
                }
            }, { toastFailure(it) })
        }

        viewModel?.let {
            it.yuejianDetails().bindLifeCycle(this).subscribe({}, { toastFailure(it) })//用邀约详情判断自定义消息是否可以点击
        }
    }


    @Subscribe
    fun onEvent(model: DivideModel) {//提交消费划分
        viewModel?.let {
            if (TextUtils.isEmpty(it.yueJIanDetailsModel.payment)) {
                it.huafen(model).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                it.yuejianDetails().bindLifeCycle(this).subscribe({}, { toastFailure(it) })//用邀约详情判断自定义消息是否可以点击
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 9, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (requestCode) {
            0 -> {
                viewModel?.let {
                    //举报选择的图片截图
                    if (it.jubaoFilePath.isNotEmpty()) {
                        it.jubaoFilePath.clear()
                    }
                    for (info in PictureSelector.obtainMultipleResult(data)) {
                        it.jubaoFilePath.add(info.path) //文件路径
                    }
                }
            }

            1 -> {//选择约见地址
                ToastUtil.showTopSnackBar(this, "选择约见时间")
                viewModel?.let {
                    it.info = data.getParcelableExtra("poi") as PoiInfo
                    it.selectTime()
                }
            }
            403 -> {//发送地址
                val poi = data.getParcelableExtra("poi") as PoiInfo

                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(
                        this@ChatActivity,
                        "com.lxkj.qiqihunshe.provider", screenshotPath
                    )
                } else {
                    Uri.fromFile(screenshotPath)
                }
                val locationMessage =
                    LocationMessage.obtain(
                        poi.location.latitude,
                        poi.location.longitude,
                        "我的位置：" + poi.name, uri
                    )

                val message =
                    io.rong.imlib.model.Message.obtain(
                        viewModel?.targetId,
                        Conversation.ConversationType.PRIVATE,
                        locationMessage
                    )
                RongYunUtil.sendLocationMessage(message)
            }
            101 -> {//支付消费划分
                if (resultCode == 303) {
                    viewModel?.let {
                        viewModel?.sendMessage2("4", data.getStringExtra("money"))
                    }
                }
            }
        }

    }


    private val screenshotPath by lazy { File("${Environment.getExternalStorageDirectory()}/DCIM/" + "Screenshots/screenshot.png") }

    override fun onDestroy() {
        super.onDestroy()
        ReportDialog2.diss()
        QiQiAssistDialog.dismiss()
        DivideDialog.dismiss()
        EventBus.getDefault().unregister(this)
    }


    //判断是否安装百度地图
    private fun isInstalled(packageName: String): Boolean {
        val manager = this.packageManager
        //获取所有已安装程序的包信息
        val installedPackages = manager.getInstalledPackages(0)
        if (installedPackages != null) {
            for (info in installedPackages) {
                if (info.packageName.equals(packageName))
                    return true
            }
        }
        return false
    }


    override fun onBackPressed() {
        EventBus.getDefault().post("redMsg")
        super.onBackPressed()
    }


}