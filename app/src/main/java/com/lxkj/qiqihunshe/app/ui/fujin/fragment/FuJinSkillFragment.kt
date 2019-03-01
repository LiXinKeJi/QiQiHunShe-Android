package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinSkillViewModel
import com.lxkj.qiqihunshe.databinding.FragmentFujinSkillBinding
import android.util.DisplayMetrics
import android.view.WindowManager
import android.content.Context
import android.view.View
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.lxkj.qiqihunshe.app.ui.dialog.VoiceTipDialog
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import kotlinx.android.synthetic.main.fragment_fujin_skill.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * Created by Slingge on 2019/2/27
 */
class FuJinSkillFragment : BaseFragment<FragmentFujinSkillBinding, FuJinSkillViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = FuJinSkillViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin_skill

    override fun init() {
        EventBus.getDefault().register(this)
        val wm = activity!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val heigh = dm.heightPixels - ControlWidthHeight.dip2px(
            activity!!,
            (45 + 44)//底部导航，tablayout
        ) - StatusBarUtil.getStatusBarHeight(activity)//，状态栏高度

        ControlWidthHeight.inputhigh(heigh, jc_video)



        iv_voice.setOnClickListener(this)

    }

    override fun loadData() {
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }

        jc_video.setUp(
            "http://fangfubao.oss-cn-beijing.aliyuncs.com/20190227160719lkgL1o.mp4",
            "", JzvdStd.SCREEN_WINDOW_NORMAL
        )
        /*  GlideUtil.glideLoad(
              activity!!,
              "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png",
              jc_video.thumbImageView
          )*/
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_voice -> {
                VoiceTipDialog.show(activity!!, "洛克贝尔")
            }
        }
    }


    override fun onStart() {
        super.onStart()
        jc_video.startVideo()
    }

    @Subscribe
    fun onEvent(next: String) {
        if(next=="next"){
            ToastUtil.showToast("继续语音")
        }
    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        VoiceTipDialog.diss()
    }

}