package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonalInfoModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonalInfoViewModel
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_personal_info.*
import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.exception.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDataFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonDynamicFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonInvitationFragment
import com.lxkj.qiqihunshe.app.ui.mine.fragment.PersonSkillFragment
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityPersonalInfoBinding
import kotlinx.android.synthetic.main.include_title.*
import java.util.*


/**
 * Created by Slingge on 2019/2/21
 */
class PersonalInfoActivity : BaseActivity<ActivityPersonalInfoBinding, PersonalInfoViewModel>(), View.OnClickListener {


    private var model = PersonalInfoModel()

    override fun getBaseViewModel() = PersonalInfoViewModel()

    override fun getLayoutId() = R.layout.activity_personal_info


    override fun init() {
        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.immersiveStatusBar(this, 0f)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(this, view_staus)
        }

        iv_edit.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.bind = binding
            if (intent.getStringExtra("userId") == null) {
                it.userId = StaticUtil.uid
                ToastUtil.showToast("请传入对方id")
            } else {
                it.userId = intent.getStringExtra("userId")
            }
            it.initViewModel()
            it.getUserData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

        val list = ArrayList<Fragment>()
        val tabList = ArrayList<String>()
        tabList.add("资料")
        tabList.add("动态")
        tabList.add("邀约")
        tabList.add("才艺")

        val bundle = Bundle()
        bundle.putString("id", viewModel!!.userId)
        val fragment1 = Fragment.instantiate(this, PersonDataFragment::class.java.name, bundle)
        val fragment2 = Fragment.instantiate(this, PersonDynamicFragment::class.java.name, bundle)
        val fragment3 = Fragment.instantiate(this, PersonInvitationFragment::class.java.name, bundle)
        val fragment4 = Fragment.instantiate(this, PersonSkillFragment::class.java.name, bundle)
        list.add(fragment1)
        list.add(fragment2)
        list.add(fragment3)
        list.add(fragment4)

        val adapter = FragmentPagerAdapter(supportFragmentManager, list, tabList)
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_edit -> {
                MyApplication.openActivity(this, PerfectInfoActivitiy::class.java)
            }
        }
    }


}