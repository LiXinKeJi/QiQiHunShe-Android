package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.AddFriendViewModel
import com.lxkj.qiqihunshe.databinding.ActivityAddfriendBinding

/**
 * Created by Slingge on 2019/3/1
 */
class AddFriendActivity : BaseActivity<ActivityAddfriendBinding, AddFriendViewModel>() {


    override fun getBaseViewModel() = AddFriendViewModel()

    override fun getLayoutId() = R.layout.activity_addfriend

    override fun init() {
        initTitle("添加")
    }

}