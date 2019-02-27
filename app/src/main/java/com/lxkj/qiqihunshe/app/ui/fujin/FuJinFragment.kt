package com.lxkj.qiqihunshe.app.ui.fujin

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import cn.jzvd.Jzvd
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.fragment.FuJInPersonFragment
import com.lxkj.qiqihunshe.app.ui.fujin.fragment.FuJinDynamicFragment
import com.lxkj.qiqihunshe.app.ui.fujin.fragment.FuJinInvitationFragment
import com.lxkj.qiqihunshe.app.ui.fujin.fragment.FuJinSkillFragment
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDataFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDynamicFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonInvitationFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonSkillFragment
import com.lxkj.qiqihunshe.databinding.FragmentFujinBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/16
 */
class FuJinFragment : BaseFragment<FragmentFujinBinding, FuJinViewModel>() {


    override fun getBaseViewModel() = FuJinViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin

    override fun init() {

        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("附近邀约")
        tabList.add("附近动态")
        tabList.add("附近人")
        tabList.add("附近才艺")

        val fragment1 = Fragment.instantiate(activity, FuJinInvitationFragment::class.java.name)
        val fragment2 = Fragment.instantiate(activity, FuJinDynamicFragment::class.java.name)
        val fragment3 = Fragment.instantiate(activity, FuJInPersonFragment::class.java.name)
        val fragment4 = Fragment.instantiate(activity, FuJinSkillFragment::class.java.name)
        list.add(fragment1)
        list.add(fragment2)
        list.add(fragment3)
        list.add(fragment4)

        val adapter = FragmentPagerAdapter(childFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)


        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                Jzvd.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(p0: Int) {
            }

        })
    }

    override fun loadData() {
    }


}