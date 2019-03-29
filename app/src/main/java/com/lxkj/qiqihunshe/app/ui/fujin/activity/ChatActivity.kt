package com.lxkj.qiqihunshe.app.ui.fujin.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.baidu.mapapi.search.core.PoiInfo
import com.leon.lfilepickerlibrary.utils.Constant
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.model.ImpressionScoreModel
import com.lxkj.qiqihunshe.app.rongrun.model.QiQiAssistModel
import com.lxkj.qiqihunshe.app.rongrun.model.ShiYueModel
import com.lxkj.qiqihunshe.app.rongrun.model.YueJianModel
import com.lxkj.qiqihunshe.app.ui.dialog.*
import com.lxkj.qiqihunshe.app.ui.fujin.model.DivideModel
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.ChatViewModel
import com.lxkj.qiqihunshe.app.ui.map.activity.ChooseAddressActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityChatDetailsBinding
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.include_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/3/12
 */
class ChatActivity : BaseActivity<ActivityChatDetailsBinding, ChatViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() = ChatViewModel()

    override fun getLayoutId() = R.layout.activity_chat_details

    override fun init() {
        EventBus.getDefault().register(this)
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener(this)
        AbStrUtil.setDrawableLeft(this, R.drawable.yh_ziliao, tv_right, 0)

        iv_del.setOnClickListener(this)
        iv_yuejian.setOnClickListener(this)
        iv_jubao.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            val uri = intent.data
            it.title = uri.getQueryParameter("title")
            it.targetId = uri.getQueryParameter("targetId")
            initTitle(it.title)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_del -> {
                iv_del.visibility = View.GONE
                tv_tip0.visibility = View.GONE
            }
            R.id.iv_yuejian -> {
                viewModel?.let {
                    it.sendMessage1()
                }
            }
            R.id.tv_right -> {
                val bundle = Bundle()
                bundle.putString("userId", viewModel?.targetId)
                MyApplication.openActivity(this, PersonalInfoActivity::class.java, bundle)
            }
            R.id.iv_jubao -> {
                if (viewModel!!.JuBaoList.isEmpty()) {
                    viewModel!!.getJuBaoConten().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                } else {
                    ReportDialog2.show(this, viewModel!!.JuBaoList)
                }
            }
        }
    }

    @Subscribe
    fun onEvent(model: EventCmdModel) {
        when (model.cmd) {
            "1" -> {//拒绝约见
                abLog.e("拒绝请求", "2")
                viewModel?.sendMessage2("1")
            }
            "2" -> {//同意约见
                abLog.e("同意请求", "3")
                viewModel?.sendMessage3()
            }
            "3" -> {//选择约见地点
                MyApplication.openActivityForResult(this, ChooseAddressActivity::class.java, 1)
            }
            "4" -> {//拒绝定位
                viewModel?.sendMessage2("6")
            }
            "5" -> {//小七协助
                QiQiAssistDialog.show(this, object : QiQiAssistDialog.QiQiAssistCallBack {
                    override fun Assist(model: QiQiAssistModel) {
                        viewModel!!.addHelp(model).bindLifeCycle(this@ChatActivity).subscribe({}, { toastFailure(it) })
                    }
                })
            }
            "6" -> {//导航
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
                DivideDialog.show(this, model.res)
            }
            "10" -> {//同意消费划分
                viewModel?.sendMessage2("4", model.res)
            }
            "11" -> {//拒绝消费划分
                viewModel?.sendMessage2("5", model.res)
            }
        }
    }

    @Subscribe
    fun onEvent(model: YueJianModel) {//同意约见地点
        viewModel?.let {
            model.taid = it.targetId
            it.yueJian(model).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }

    @Subscribe
    fun onEvent(model: ShiYueModel) {//某方失约
        viewModel?.let {
            it.shiYue(model).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }


    @Subscribe
    fun onEvent(model: DivideModel) {//提交消费划分
        viewModel?.let {
            it.huafen(model).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
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
        if (requestCode == 0) {//举报选择的图片截图
            val list = data.getStringArrayListExtra(Constant.RESULT_INFO)//文件路径
            Toast.makeText(applicationContext, "选中的路径为" + list[0], Toast.LENGTH_SHORT).show()
        } else if (requestCode == 1) {//选择约见地址
            ToastUtil.showTopSnackBar(this, "选择约见时间")
            viewModel?.let {
                it.info = data.getParcelableExtra("poi") as PoiInfo
                it.selectTime()
            }
        }
    }


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


}