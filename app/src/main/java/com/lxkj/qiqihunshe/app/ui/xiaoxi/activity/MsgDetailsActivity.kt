package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.MsgDetailsViewModel

/**
 * Created by Slingge on 2019/3/1
 */
class MsgDetailsActivity :
    BaseActivity<com.lxkj.qiqihunshe.databinding.ActivityMsgDetailsBinding, MsgDetailsViewModel>() {

    override fun getBaseViewModel() = MsgDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_msg_details

    override fun init() {

        initTitle("消息详情")



    }
}