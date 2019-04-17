package com.lxkj.qiqihunshe.app.ui.shouye

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.ScreenShouYeDialog
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDataFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDynamicFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonInvitationFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonSkillFragment
import com.lxkj.qiqihunshe.app.ui.shouye.fragment.ShouYe4Fragment
import com.lxkj.qiqihunshe.app.util.SharedPreferencesUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.databinding.FragmentShouyeBinding
import kotlinx.android.synthetic.main.fragment_shouye.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/16
 */
class ShouYeFragment : BaseFragment<FragmentShouyeBinding, ShouYeViewModel>() {


    override fun getBaseViewModel() = ShouYeViewModel()

    override fun getLayoutId() = R.layout.fragment_shouye

    private var flag = 0

    override fun init() {
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(activity, Color.parseColor("#2d91ff"))
        }
        if (!SharedPreferencesUtil.getSharePreBoolean(activity, "isSlide")) {//仅显示一次
            iv_slide.visibility = View.VISIBLE
        }

        val list = ArrayList<Fragment>()

        var bundle = Bundle()
        bundle.putInt("flag", 0)
        val fragment1 = Fragment.instantiate(activity, ShouYe4Fragment::class.java.name, bundle)
        bundle = Bundle()
        bundle.putInt("flag", 1)
        val fragment2 = Fragment.instantiate(activity, ShouYe4Fragment::class.java.name, bundle)
        bundle = Bundle()
        bundle.putInt("flag", 2)
        val fragment3 = Fragment.instantiate(activity, ShouYe4Fragment::class.java.name, bundle)
        bundle = Bundle()
        bundle.putInt("flag", 3)
        val fragment4 = Fragment.instantiate(activity, ShouYe4Fragment::class.java.name, bundle)
        list.add(fragment1)
        list.add(fragment2)
        list.add(fragment3)
        list.add(fragment4)

        val adapter = FragmentPagerAdapter(childFragmentManager, list)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4

        viewPager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {

                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                }

                override fun onPageSelected(p0: Int) {
                    flag = p0

                    if (flag == 3) {
                        SharedPreferencesUtil.putSharePre(activity, "isSlide", true)//仅显示一次
                        iv_slide.visibility = View.GONE
                    }
                }
            })


        iv_screen.setOnClickListener { ScreenShouYeDialog.show(activity!!, flag) }


    }

    override fun loadData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        ScreenShouYeDialog.diss()
    }

}