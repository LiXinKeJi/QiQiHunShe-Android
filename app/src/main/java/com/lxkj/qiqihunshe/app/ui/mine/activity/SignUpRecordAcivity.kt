package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SignUpRecordViewModel
import com.lxkj.qiqihunshe.databinding.ActivitySignupRecordBinding

/**
 * Created by Slingge on 2019/2/22
 */
class SignUpRecordAcivity : BaseActivity<ActivitySignupRecordBinding, SignUpRecordViewModel>() {


    override fun getBaseViewModel() = SignUpRecordViewModel()

    override fun getLayoutId() = R.layout.activity_signup_record

    override fun init() {
        WhiteStatusBar()
        initTitle("报名记录")

        viewModel?.let {
            binding.viewmodel=it
            it.bind=binding
            it.initViewModel()
        }
    }


}