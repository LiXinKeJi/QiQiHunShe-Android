package com.lxkj.qiqihunshe.app.ui.mine.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonSkillViewModel
import com.lxkj.qiqihunshe.databinding.FragmentPersonSkillBinding

/**
 * 才艺
 * Created by Slingge on 2019/2/21
 */
class PersonSkillFragment : BaseFragment<FragmentPersonSkillBinding, PersonSkillViewModel>() {


    override fun getBaseViewModel() = PersonSkillViewModel()

    override fun getLayoutId() = R.layout.fragment_person_skill

    override fun init() {


    }

    override fun loadData() {
        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
        }
    }


}