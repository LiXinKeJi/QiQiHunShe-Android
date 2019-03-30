package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.AboutMeFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.ActivityRecordFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.CommentRecordFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.QiQiRecommendFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRecommendViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRecommendBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.activity_qiqi_recommend.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/22
 */
class InteractiveNotificationActivity : BaseActivity<ActivityQiqiRecommendBinding, QiQiRecommendViewModel>() {

    override fun getBaseViewModel() = QiQiRecommendViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_recommend

    override fun init() {
        EventBus.getDefault().register(this)
        WhiteStatusBar()
        initTitle("互动通知")

        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("谁喜欢我")
        tabList.add("谁看过我")
        tabList.add("点评记录")
        tabList.add("活动记录")

        var bundle = Bundle()
        bundle.putInt("flag", 1)
        val fragment0 = Fragment.instantiate(this, AboutMeFragment::class.java.name, bundle)
        list.add(fragment0)

        bundle = Bundle()
        bundle.putInt("flag", 2)
        val fragment1 = Fragment.instantiate(this, AboutMeFragment::class.java.name, bundle)
        list.add(fragment1)

        val fragment2 = Fragment.instantiate(this, CommentRecordFragment::class.java.name)
        list.add(fragment2)

        val fragment3 = Fragment.instantiate(this, ActivityRecordFragment::class.java.name)
        list.add(fragment3)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tabs.setupWithViewPager(viewPager)

    }


    @Subscribe
    fun onEvent(mode: XxModel) {
        MyApplication.setRedNum(tv_msgNum1, mode.love.toInt())
        MyApplication.setRedNum(tv_msgNum2, mode.look.toInt())
        MyApplication.setRedNum(tv_msgNum3, mode.comment.toInt())
        MyApplication.setRedNum(tv_msgNum4, mode.activity.toInt())
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}