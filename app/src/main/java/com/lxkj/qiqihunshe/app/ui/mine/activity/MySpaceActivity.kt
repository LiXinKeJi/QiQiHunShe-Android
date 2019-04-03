package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.*
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MySpaceViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityMyspaceBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_title.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/25
 */
class MySpaceActivity : BaseActivity<ActivityMyspaceBinding, MySpaceViewModel>() {


    override fun getBaseViewModel() = MySpaceViewModel()

    override fun getLayoutId() = R.layout.activity_myspace

    val list = ArrayList<Fragment>()

    private var flag = 0

    override fun init() {
        initTitle("我的空间")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "发布动态"

        tv_right.setOnClickListener {
            when (flag) {
                0 -> {
                    if (!StaticUtil.isRealNameAuth(this)) {
                        return@setOnClickListener
                    }
                    val bundle = Bundle()
                    bundle.putInt("flag", 0)
                    MyApplication.openActivityForResult(this, ReleaseDynamicActivity::class.java, bundle, 0)
                }
                1 -> {
                    if (!StaticUtil.isRealNameAuth(this)) {
                        return@setOnClickListener
                    }
                    MyApplication.openActivityForResult(this, ReleaseSkillActivity::class.java, 1)
                }
                2 -> {
                    if (!StaticUtil.isRealNameAuth(this)) {
                        return@setOnClickListener
                    }

                    val bundle = Bundle()
                    bundle.putInt("type", 0)
                    MyApplication.openActivityForResult(this, ReleaseInvitationTypeActivity::class.java, bundle, 2)
                }
            }
        }


        val tabList = ArrayList<String>()
        tabList.add("我的动态")
        tabList.add("我的才艺")
        tabList.add("我的邀约")

        val fragment1 = Fragment.instantiate(this, SpaceDynamicFragment::class.java.name)
        val fragment2 = Fragment.instantiate(this, SpaceSkillFragment::class.java.name)
        val fragment3 = Fragment.instantiate(this, SpaceInvitationFragment::class.java.name)
        list.add(fragment1)
        list.add(fragment2)
        list.add(fragment3)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                flag = p0
                when (p0) {
                    0 -> {
                        tv_right.text = "发布动态"
                    }
                    1 -> {
                        tv_right.text = "发布才艺"
                    }
                    2 -> {
                        tv_right.text = "发布邀约"
                    }
                }
            }
        })

        viewPager.offscreenPageLimit = 3
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        list[0].onActivityResult(requestCode, resultCode, data)
        list[1].onActivityResult(requestCode, resultCode, data)
        list[2].onActivityResult(requestCode, resultCode, data)
    }


}