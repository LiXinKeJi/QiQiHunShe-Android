package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.support.v4.app.Fragment
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.*
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AffectiveZoneViewModel
import com.lxkj.qiqihunshe.databinding.ActivityAffectiveZoneBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_title.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/25
 */
class AffectiveZoneActivity : BaseActivity<ActivityAffectiveZoneBinding, AffectiveZoneViewModel>() {

    override fun getBaseViewModel() = AffectiveZoneViewModel()


    override fun getLayoutId() = R.layout.activity_affective_zone
    override fun init() {
        initTitle("情感专区")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "我的情感"
        tv_right.setOnClickListener {

        }



        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("情感动态")
        tabList.add("情感征婚")

        val fragment1 = Fragment.instantiate(this, AffectiveDynamicFragment::class.java.name)
        val fragment2 = Fragment.instantiate(this, AffectiveMarriageFragment::class.java.name)
        list.add(fragment1)
        list.add(fragment2)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }


}