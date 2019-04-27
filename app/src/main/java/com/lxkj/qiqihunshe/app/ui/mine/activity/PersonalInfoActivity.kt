package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonalInfoModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonalInfoViewModel
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_personal_info.*
import android.support.v4.app.Fragment
import android.text.TextUtils
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.exception.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDataFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDynamicFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonInvitationFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonSkillFragment
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityPersonalInfoBinding
import io.rong.imkit.RongIM
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


/**
 * Created by Slingge on 2019/2/21
 */
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding, PersonalInfoViewModel>(), View.OnClickListener {


    private var model = PersonalInfoModel()

    override fun getBaseViewModel() = PersonalInfoViewModel()

    override fun getLayoutId() = R.layout.activity_personal_info
    val list = ArrayList<Fragment>()

    override fun init() {
        EventBus.getDefault().register(this)
        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.immersiveStatusBar(this, 0f)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(this, view_staus)
        }

        iv_back.setOnClickListener(this)
        iv_edit.setOnClickListener(this)
        tv_vido.setOnClickListener(this)
        cv_fllow.setOnClickListener(this)

        tv_cancel.setOnClickListener(this)
        tv_conversation.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.bind = binding
            if (intent.getStringExtra("userId") == null) {
                it.userId = StaticUtil.uid
            } else {
                it.userId = intent.getStringExtra("userId")
            }
            it.initViewModel()
            it.getUserData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }


        val tabList = ArrayList<String>()
        tabList.add("资料")
        tabList.add("动态")
        tabList.add("邀约")
        tabList.add("才艺")

        val bundle = Bundle()
        bundle.putString("id", viewModel!!.userId)
        val fragment1 = Fragment.instantiate(this, PersonDataFragment::class.java.name, bundle)
        val fragment2 = Fragment.instantiate(this, PersonDynamicFragment::class.java.name, bundle)
        val fragment3 = Fragment.instantiate(this, PersonInvitationFragment::class.java.name, bundle)
        val fragment4 = Fragment.instantiate(this, PersonSkillFragment::class.java.name, bundle)
        list.add(fragment1)
        list.add(fragment2)
        list.add(fragment3)
        list.add(fragment4)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
        tabs.setupWithViewPager(viewPager)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.iv_edit -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(this, PerfectInfoActivitiy::class.java, bundle)

            }
            R.id.tv_vido -> {
                if (TextUtils.isEmpty(viewModel?.model?.video)) {
                    ToastUtil.showTopSnackBar(this, "未上传视频")
                    return
                }
                if (banner.visibility == View.VISIBLE) {
                    banner.visibility = View.INVISIBLE
                    jz_video.visibility = View.VISIBLE
                    tv_vido.text = "切换图片"
                    jz_video.setUp(
                        viewModel?.model?.video,
                        "", JzvdStd.SCREEN_WINDOW_NORMAL
                    )
                    jz_video.startVideo()
                } else {
                    tv_vido.text = "切换3秒视频"
                    Jzvd.releaseAllVideos()
                    banner.visibility = View.VISIBLE
                    jz_video.visibility = View.INVISIBLE
                }
            }
            R.id.cv_fllow -> {//喜欢
                if (viewModel!!.userId == StaticUtil.uid) {
                    return
                }
                viewModel!!.floow().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
            R.id.tv_cancel -> {
                finish()
            }
            R.id.tv_conversation -> {
                RongYunUtil.toChat(this, viewModel!!.userId, viewModel!!.model.nickname)
            }
        }
    }

    private var marriage = ""
    @Subscribe
    fun onEvent(marriage: String) {// 情感状态 0未婚 1已婚 2离异
        this.marriage = marriage
        if (marriage == "1") {
            viewModel!!.isFirend()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        list[1].onActivityResult(requestCode, resultCode, data)
    }


    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}