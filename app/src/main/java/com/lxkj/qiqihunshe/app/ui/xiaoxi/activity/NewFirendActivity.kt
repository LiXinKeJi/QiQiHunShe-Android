package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.NewFirendViewModel
import com.lxkj.qiqihunshe.databinding.ActivityNewFriendBinding

/**
 * Created by Slingge on 2019/3/1
 */
class NewFirendActivity : BaseActivity<ActivityNewFriendBinding, NewFirendViewModel>() {

    override fun getBaseViewModel() = NewFirendViewModel()

    override fun getLayoutId() = R.layout.activity_new_friend

    override fun init() {
        initTitle("新的朋友")

        viewModel?.let {
            it.bind=binding
            it.init()
        }
    }

}