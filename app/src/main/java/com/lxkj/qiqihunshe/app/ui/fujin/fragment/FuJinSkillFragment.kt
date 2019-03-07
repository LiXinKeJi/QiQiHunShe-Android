package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.support.v4.app.Fragment
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinSkillViewModel
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.SkillViewModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.databinding.FragmentFujinSkillBinding
import kotlinx.android.synthetic.main.fragment_fujin_skill.*
import java.util.ArrayList


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

    }


}