package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicBinding

/**
 * Created by Slingge on 2019/2/21
 */
class QiQIDynamicActivity : BaseActivity<ActivityQiqiDynamicBinding, QiQiDynamicViewModel>() {


    override fun getBaseViewModel() = QiQiDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_dynamic

    override fun init() {
        WhiteStatusBar()
        initTitle("七七活动")

        viewModel?.let {
            binding.viewmodel=it
            it.bind=binding
            it.initViewModel()
        }
    }

}