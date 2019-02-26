package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.*
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyAffectiveViewmodel
import com.lxkj.qiqihunshe.databinding.ActivityMyaffectiveBinding
import kotlinx.android.synthetic.main.activity_personal_info.*
import kotlinx.android.synthetic.main.include_title.*
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/26
 */
class MyAffectiveActivity : BaseActivity<ActivityMyaffectiveBinding, MyAffectiveViewmodel>() {

    override fun getBaseViewModel() = MyAffectiveViewmodel()

    override fun getLayoutId() = R.layout.activity_myaffective

    private var flag=0//0发布问题，1发布征婚

    override fun init() {
        initTitle("我的情感")

        tv_right.visibility= View.VISIBLE
        tv_right.text="发布问题"
        tv_right.setOnClickListener {
            if(flag==0){
                MyApplication.openActivity(this,ReleaseDynamicActivity::class.java)
            }else{
                MyApplication.openActivity(this,ReleaseInvitationTypeActivity::class.java)
            }
        }

        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("我的问题")
        tabList.add("我的征婚")

        val fragment1 = Fragment.instantiate(this, MyProblemFragment::class.java.name)
        val fragment2 = Fragment.instantiate(this, MyMarriageFragment::class.java.name)
        list.add(fragment1)
        list.add(fragment2)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                flag=p0
            }
        })
    }


}