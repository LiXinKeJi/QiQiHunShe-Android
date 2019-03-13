package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinSkillViewModel
import com.lxkj.qiqihunshe.databinding.FragmentFujinSkillBinding


/**
 * Created by Slingge on 2019/2/27
 */
class FuJinSkillFragment : BaseFragment<FragmentFujinSkillBinding, FuJinSkillViewModel>() {


    override fun getBaseViewModel() = FuJinSkillViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin_skill

    override fun init() {
        viewModel?.let {
            it.bind = binding
            it.init()
        }

    }


    override fun loadData() {
        viewModel?.getNearbyCaiyi()
    }


}