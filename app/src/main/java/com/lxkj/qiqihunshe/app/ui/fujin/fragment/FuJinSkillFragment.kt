package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinSkillViewModel
import com.lxkj.qiqihunshe.databinding.FragmentFujinSkillBinding
import android.util.DisplayMetrics
import android.view.WindowManager
import android.content.Context
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_fujin_skill.*


/**
 * Created by Slingge on 2019/2/27
 */
class FuJinSkillFragment : BaseFragment<FragmentFujinSkillBinding, FuJinSkillViewModel>() {


    override fun getBaseViewModel() = FuJinSkillViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin_skill

    override fun init() {

        val wm = activity!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        val heigh = dm.heightPixels - ControlWidthHeight.dip2px(
            activity!!,
            (45 + 44)//底部导航，tablayout
        ) - StatusBarUtil.getStatusBarHeight(activity)//，状态栏高度

        ControlWidthHeight.inputhigh(heigh, jc_video)

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

    override fun loadData() {
        jc_video.startVideo()
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }
    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


}