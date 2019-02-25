package com.lxkj.qiqihunshe.app.ui.mine.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.activity.ChargingSetUpActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.SeenSkillActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SpaceSkillViewModel
import com.lxkj.qiqihunshe.databinding.FragmentSpaceSkillBinding
import kotlinx.android.synthetic.main.fragment_space_skill.*

/**
 * 我的空间- 我的邀约
 * Created by Slingge on 2019/2/25
 */
class SpaceSkillFragment : BaseFragment<FragmentSpaceSkillBinding, SpaceSkillViewModel>() {
    override fun getBaseViewModel() = SpaceSkillViewModel()

    override fun getLayoutId()= R.layout.fragment_space_skill

    override fun init() {
       viewModel?.let {
           it.bind=binding
           it.initViewModel()
       }

        tv_setup.setOnClickListener {
            MyApplication.openActivity(activity,ChargingSetUpActivity::class.java)
        }
        tv_see.setOnClickListener {
            MyApplication.openActivity(activity,SeenSkillActivity::class.java)
        }

    }

    override fun loadData() {
    }


}