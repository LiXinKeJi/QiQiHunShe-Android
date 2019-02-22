package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.ActivityRecordFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.CommentRecordFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.QiQiRecommendFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRecommendViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRecommendBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/22
 */
class InteractiveNotificationActivity : BaseActivity<ActivityQiqiRecommendBinding, QiQiRecommendViewModel>() {

    override fun getBaseViewModel() = QiQiRecommendViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_recommend

    override fun init() {
        WhiteStatusBar()
        initTitle("互动通知")


        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("谁喜欢我")
        tabList.add("谁看过我")
        tabList.add("点评记录")
        tabList.add("活动记录")

        var bundle = Bundle()
        bundle.putInt("flag", 2)
        val fragment0 = Fragment.instantiate(this, QiQiRecommendFragment::class.java.name, bundle)
        list.add(fragment0)

        bundle = Bundle()
        bundle.putInt("flag", 3)
        val fragment1 = Fragment.instantiate(this, QiQiRecommendFragment::class.java.name, bundle)
        list.add(fragment1)

        val fragment2 = Fragment.instantiate(this, CommentRecordFragment::class.java.name)
        list.add(fragment2)

        val fragment3 = Fragment.instantiate(this, ActivityRecordFragment::class.java.name)
        list.add(fragment3)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }


}