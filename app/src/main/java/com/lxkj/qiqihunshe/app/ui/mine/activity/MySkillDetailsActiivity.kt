package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MySkillDetailsViewModel
import com.lxkj.qiqihunshe.databinding.ActiviyMyskillDetailsBinding

/**
 * Created by Slingge on 2019/3/7
 */
class MySkillDetailsActiivity : BaseActivity<ActiviyMyskillDetailsBinding, MySkillDetailsViewModel>() {

    override fun getBaseViewModel() = MySkillDetailsViewModel()

    override fun getLayoutId() = R.layout.activiy_myskill_details


    override fun init() {

        viewModel?.let {
            it.fragmentManager=supportFragmentManager
            it.getSkill()
        }

    }




}