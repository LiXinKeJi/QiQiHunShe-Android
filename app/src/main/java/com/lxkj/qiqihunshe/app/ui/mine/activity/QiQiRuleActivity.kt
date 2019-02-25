package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRuleViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRuleBinding

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleActivity : BaseActivity<ActivityQiqiRuleBinding, QiQiRuleViewModel>() {


    override fun getBaseViewModel() = QiQiRuleViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_rule

    override fun init() {
        initTitle("七七规则")

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
        }
    }

}