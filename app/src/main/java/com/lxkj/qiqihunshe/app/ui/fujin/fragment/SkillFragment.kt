package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.exception.bindLifeCycle
import com.lxkj.qiqihunshe.app.service.CallKitService
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.dialog.VoiceTipDialog
import com.lxkj.qiqihunshe.app.ui.fujin.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.SkillViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.FragmentSkillBinding
import io.rong.callkit.RongCallKit
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.fragment_skill.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/3/2
 */
class SkillFragment : BaseFragment<FragmentSkillBinding, SkillViewModel>(), View.OnClickListener,
    BGARefreshLayout.BGARefreshLayoutDelegate, CallKitService.CallKitEndCallBack {


    var model: DataListModel? = null


    override fun getBaseViewModel() = SkillViewModel()

    override fun getLayoutId() = R.layout.fragment_skill

    private var callService: CallKitService? = null
    val intent by lazy { Intent(activity, CallKitService::class.java) }


    override fun init() {

        model = arguments?.getSerializable("model") as DataListModel
        viewModel?.model = model


        //视频封面图
        GlideUtil.glideLoad(context, model?.image, jc_video?.thumbImageView)
        //用户头像
        GlideUtil.glideLoad(context, model?.icon, iv_header)
        tv_playnum?.text = "播放量：" + model?.count
        tv_time.text = model?.adtime
        tv_name.text = model?.title
        tv_address.text = model?.location
        tv_content.text = model?.content


        val wm = activity!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val heigh = dm.heightPixels - ControlWidthHeight.dip2px(
            activity!!,
            (45 + 44 + 48)//底部导航，tablayout
        ) - StatusBarUtil.getStatusBarHeight(activity)//，状态栏高度

        ControlWidthHeight.inputhigh(heigh, jc_video)

        iv_voice.setOnClickListener(this)
        iv_video.setOnClickListener(this)
        iv_dashang.setOnClickListener(this)
        iv_send.setOnClickListener(this)

        viewModel?.let {
            it.bind = binding
            it.initViewModel()
            it.getBannel().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

    }


    override fun loadData() {
        EventBus.getDefault().register(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_voice -> {
                viewModel?.type = 1
                VoiceTipDialog.show(activity!!, model!!.userName, "语音", model!!.voice)
            }
            R.id.iv_video -> {
                viewModel?.type = 2
                VoiceTipDialog.show(activity!!, model!!.userName, "视频", model!!.video)
            }
            R.id.iv_dashang -> {
                DaShangDialog.show(activity!!, object : DaShangDialog.DaShangCallBack {
                    override fun dashang(money: String) {
                        viewModel!!.dashang(money).bindLifeCycle(this@SkillFragment).subscribe({}, { toastFailure(it) })
                    }
                })
            }
            R.id.iv_send -> {
                if (StringUtil.isEmpty(et_comment.text.toString())) {
                    ToastUtil.showTopSnackBar(activity, "请输入评论内容！")
                } else
                    viewModel?.addCaiyiComment(et_comment.text.toString())
            }
        }
    }


    override fun onStart() {
        super.onStart()
        jc_video.setUp(
            model?.videoUrl,
            "", JzvdStd.SCREEN_WINDOW_NORMAL
        )
    }

    override fun onResume() {
        super.onResume()
        jc_video.startVideo()
        viewModel?.playCaiyi()
    }

    @Subscribe
    fun onEvent(next: String) {
        if (next == "next") {
            when (viewModel?.type) {
                1 -> {//音频通话
                    viewModel?.let {
                        abLog.e("费用", StaticUtil.amount + ",,,," + model!!.voice)
                        if (StaticUtil.amount.toDouble() < model!!.voice.toDouble()) {
                            ToastUtil.showTopSnackBar(activity, "余额不足")
                            return
                        }
                        it.money = it.model!!.voice
                        openService()
                    }
                    RongCallKit.startSingleCall(
                        activity, model!!.userId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO
                    )
                }
                2 -> {//视频通话
                    viewModel?.let {
                        if (StaticUtil.amount.toDouble() < model!!.video.toDouble()) {
                            ToastUtil.showTopSnackBar(activity, "余额不足")
                            return
                        }
                        it.money = it.model!!.video
                        openService()
                    }
                    RongCallKit.startSingleCall(
                        activity, model!!.userId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO
                    )
                }
            }
        }
    }

    fun openService() {
        if (viewModel?.type == 1) {//音频
            intent.putExtra("price", model!!.voice)
        } else {
            intent.putExtra("price", model!!.video)
        }
        activity!!.bindService(intent, locationConnection, Context.BIND_AUTO_CREATE)
        activity!!.startService(intent)
    }

    override fun callEnd(minute: String, adtime: String) {
        val json = "{\"cmd\":\"changefee\",\"uid\":\"" + StaticUtil.uid + "\",\"taid\":\"" + model?.userId +
                "\",\"price\":\"" + viewModel?.money + "\",\"minute\":\"" + minute + "\",\"type\":\"" + viewModel?.type +
                "\",\"adtime\":\"" + adtime + "\"}"
        abLog.e("通话", json)
        viewModel?.changefee(json)!!.bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


    private val locationConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CallKitService.Binder
            callService = binder.service
            callService?.sCallKitEndCallBac(this@SkillFragment)
        }

        override fun onServiceDisconnected(className: ComponentName) {}
    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        VoiceTipDialog.diss()
        activity!!.stopService(intent)
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        if (viewModel!!.page >= viewModel!!.totalPage) {
            return false
        }
        viewModel?.getCaiyiCommentList()
        return true
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
    }

}