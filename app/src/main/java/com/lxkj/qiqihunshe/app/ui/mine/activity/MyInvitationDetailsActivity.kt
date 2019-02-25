package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyInvitationDetailsViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMyinvitationDetailsBinding

/**
 * 我的邀约详情
 * Created by Slingge on 2019/2/25
 */
class MyInvitationDetailsActivity :
    BaseActivity<ActivityMyinvitationDetailsBinding, MyInvitationDetailsViewModel>() {


    override fun getBaseViewModel() = MyInvitationDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_myinvitation_details
    override fun init() {
        initTitle("邀约详情")
        viewModel?.let {
            it.bind = binding
            it.init()
        }
    }


}