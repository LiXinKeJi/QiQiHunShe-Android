package com.lxkj.qiqihunshe.app.ui

import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {


    override fun getBaseViewModel() = MainViewModel()

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {

        StatusBarUtil.transparentStatusBar(this)

        viewModel?.let {
            it.bind = binding
            it.framanage=supportFragmentManager
            it.initBind()
        }

    }


}
