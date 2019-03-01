package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.HunSheViewModel
import com.lxkj.qiqihunshe.databinding.FragmentHunsheBinding
import kotlinx.android.synthetic.main.fragment_hunshe.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/28
 */
class HunSheFragment : BaseFragment<FragmentHunsheBinding, HunSheViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = HunSheViewModel()

    override fun getLayoutId() = R.layout.fragment_hunshe

    override fun init() {

        iv_cancel.setOnClickListener(this)
        rb_xiangshi.setOnClickListener(this)
        rb_yuehui.setOnClickListener(this)
        rb_qianshou.setOnClickListener(this)
        rb_xiangshi.isChecked = true


        val list = ArrayList<Fragment>()

        val fragment1 = Fragment.instantiate(activity, XiangShiFragment::class.java.name)
        val fragment2 = Fragment.instantiate(activity, YueHuiFragment::class.java.name)
        val fragment3 = Fragment.instantiate(activity, QianShouFragment::class.java.name)
        list.add(fragment1)
        list.add(fragment2)
        list.add(fragment3)
        val adapter = FragmentPagerAdapter(childFragmentManager, list)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                select(p0)
            }

        })

    }

    override fun loadData() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_cancel -> {
                tv_tip.visibility = View.GONE
                line1.visibility = View.GONE
                iv_cancel.visibility = View.GONE
            }
            R.id.rb_xiangshi -> {
                viewPager.currentItem=0
                select(0)
            }
            R.id.rb_yuehui -> {
                viewPager.currentItem=1
                select(1)
            }
            R.id.rb_qianshou -> {
                viewPager.currentItem=2
                select(2)
            }
        }
    }


    private fun select(flag: Int) {

        when (flag) {
            0->{
                rb_xiangshi.isChecked = true
                rb_qianshou.isChecked = false
                rb_yuehui.isChecked = false
            }
            1->{
                rb_yuehui.isChecked = true
                rb_qianshou.isChecked = false
                rb_xiangshi.isChecked = false
            }
            2->{
                rb_yuehui.isChecked = false
                rb_xiangshi.isChecked = false
                rb_qianshou.isChecked = true
            }
        }


    }


}