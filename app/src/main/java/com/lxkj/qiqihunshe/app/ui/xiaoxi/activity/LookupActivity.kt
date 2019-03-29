package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.LookupAccurateFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.LookupConditionFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.LookupEconomicsFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupViewModel
import com.lxkj.qiqihunshe.databinding.ActivityLookupBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/3/1
 */
class LookupActivity : BaseActivity<ActivityLookupBinding, LookupViewModel>() {


    override fun getBaseViewModel() = LookupViewModel()

    override fun getLayoutId() = R.layout.activity_lookup

    private var flag = -1//0精确查找，1条件，2经济,3选择牵手人

    private val fragment1 by lazy {  Fragment.instantiate(this, LookupAccurateFragment::class.java.name) }

    override fun init() {
        initTitle("精准查找")

        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("精确查找")
        tabList.add("条件查找")
        tabList.add("经济查找")

        val fragment2 = Fragment.instantiate(this, LookupConditionFragment::class.java.name)
        val fragment3 = Fragment.instantiate(this, LookupEconomicsFragment::class.java.name)

        flag = intent.getIntExtra("flag", -1)
        val bundle = Bundle()
        bundle.putInt("flag", flag)
        fragment1.arguments = bundle
        list.add(fragment1)
        if (flag != 3) {
            list.add(fragment2)
            list.add(fragment3)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        flag = intent.getIntExtra("flag", 0)
        if (flag == 3) {
            viewPager.currentItem = 0
        } else {
            viewPager.currentItem = flag
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment1.onActivityResult(requestCode, resultCode, data)
    }


}