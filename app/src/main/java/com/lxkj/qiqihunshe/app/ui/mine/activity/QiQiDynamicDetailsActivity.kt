package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiDynamicDetailsViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicDetailsBinding

/**
 * Created by Slingge on 2019/2/21
 */
class QiQIDynamicDetailsActivity : BaseActivity<ActivityQiqiDynamicDetailsBinding, QiQiDynamicDetailsViewModel>() {


    override fun getBaseViewModel() = QiQiDynamicDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_dynamic_details

    override fun init() {
        initTitle("活动详情")

        viewModel?.let {
            binding.viewmodel=it

        }
    }

}