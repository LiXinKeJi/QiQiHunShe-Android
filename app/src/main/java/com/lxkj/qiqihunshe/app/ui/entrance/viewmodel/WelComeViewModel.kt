package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.fragment.WelComeFragment
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil
import com.lxkj.qiqihunshe.databinding.ActivityWelcomeBinding
import java.util.ArrayList

/**
 * Created by Slingge on 2019/4/4
 */
class WelComeViewModel : BaseViewModel(), WelComeFragment.WelComeNextCallBack {

    val list by lazy { ArrayList<Fragment>() }

    lateinit var bind: ActivityWelcomeBinding

    lateinit var supportFragmentManager: FragmentManager


    fun init() {
        for (i in 0..6) {
            val bundle = Bundle()
            bundle.putInt("flag", i)
            val fragment1 = Fragment.instantiate(activity, WelComeFragment::class.java.name, bundle) as WelComeFragment
            fragment1.SWelComeNextCallBack(this)
            list.add(fragment1)
        }

        val adapter = FragmentPagerAdapter(supportFragmentManager, list)
        bind.viewPager.adapter = adapter

        bind.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageScrollStateChanged(p0: Int) {
                when (p0) {
                    ViewPager.SCROLL_STATE_DRAGGING -> isState = true
                    ViewPager.SCROLL_STATE_SETTLING -> isState = false
                    ViewPager.SCROLL_STATE_IDLE -> {
                        /**
                         * 判断是不是最后一页，同时是不是拖的状态
                         */
                        if ((bind.viewPager.currentItem == (adapter.count - 1)) && isState) {
                            SharedPreferencesUtil.putSharePre(activity, "isFirst", true)
                            MyApplication.openActivity(activity, MainActivity::class.java)
                        }
                        isState = true
                    }
                }//拖的时候才进入下一页
            }

            override fun onPageSelected(p0: Int) {

            }
        })
    }

    private var isState: Boolean = false

    fun viewPagerSlide(flag: Int) {
        when (flag) {
            0 -> bind.viewPager.currentItem = 1
            1 -> bind.viewPager.currentItem = 2
            2 -> bind.viewPager.currentItem = 3
            3 -> bind.viewPager.currentItem = 4
            4 -> bind.viewPager.currentItem = 5
            5 -> bind.viewPager.currentItem = 6
            6 -> {
                MyApplication.openActivity(activity, MainActivity::class.java)
            }
        }
    }


    override fun welCome(flag: Int) {
        viewPagerSlide(flag)
    }


}