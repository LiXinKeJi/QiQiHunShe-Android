package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.*
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRecommendViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRecommendBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/22
 */
class QiQiRecommendActivity : BaseActivity<ActivityQiqiRecommendBinding, QiQiRecommendViewModel>() {


    override fun getBaseViewModel() = QiQiRecommendViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_recommend

    override fun init() {
        WhiteStatusBar()
        initTitle("七七推荐")


        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("小七推荐")
        tabList.add("定制推荐")

        var bundle = Bundle()
        bundle.putInt("flag", 0)
        val fragment0 = Fragment.instantiate(this, QiQiRecommendFragment::class.java.name, bundle)
        list.add(fragment0)

        bundle = Bundle()
        bundle.putInt("flag", 1)
        val fragment1 = Fragment.instantiate(this, QiQiRecommendFragment::class.java.name, bundle)
        list.add(fragment1)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }


}