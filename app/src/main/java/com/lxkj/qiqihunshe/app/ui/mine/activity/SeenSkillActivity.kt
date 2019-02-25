package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SeenSkillViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/25
 */
class SeenSkillActivity : BaseActivity<ActivityRecyvlerviewBinding, SeenSkillViewModel>() {


    override fun getBaseViewModel() = SeenSkillViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        initTitle("我看过的才艺")
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }
    }
}