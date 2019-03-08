package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.lxkj.huaihuatransit.app.util.ControlWidthHeight
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.dialog.VoiceTipDialog
import com.lxkj.qiqihunshe.app.ui.fujin.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.SkillViewModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.app.util.StringUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentSkillBinding
import kotlinx.android.synthetic.main.fragment_skill.*
import kotlinx.android.synthetic.main.item_image.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/3/2
 */
class SkillFragment : BaseFragment<FragmentSkillBinding, SkillViewModel>(), View.OnClickListener,
    BGARefreshLayout.BGARefreshLayoutDelegate {


    var model: DataListModel? = null
    var type = 0 //通话类型 0 语音 1 视频

    override fun getBaseViewModel() = SkillViewModel()

    override fun getLayoutId() = R.layout.fragment_skill

    override fun init() {
        var model = arguments?.getSerializable("model") as DataListModel
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
        }


        bgRefreshLayout.setPullDownRefreshEnable(false)
        bgRefreshLayout.setDelegate(this)
        bgRefreshLayout.setRefreshViewHolder(BGANormalRefreshViewHolder(context, true))

    }


    override fun loadData() {
        EventBus.getDefault().register(this)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_voice -> {
                type = 0
                VoiceTipDialog.show(activity!!, model!!.userName, "语音")
            }
            R.id.iv_video -> {
                type = 1
                VoiceTipDialog.show(activity!!, model!!.userName, "视频")
            }
            R.id.iv_dashang -> {
                DaShangDialog.show(activity!!)
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
            when (type) {
                0 -> {
                    ToastUtil.showTopSnackBar(this, "语音通话")
                }
                1 -> {
                    ToastUtil.showTopSnackBar(this, "视频通话")
                }
            }

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