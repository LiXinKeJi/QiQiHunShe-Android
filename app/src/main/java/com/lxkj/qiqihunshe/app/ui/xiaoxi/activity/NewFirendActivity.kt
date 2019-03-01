package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.NewFirendViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/3/1
 */
class NewFirendActivity : BaseActivity<ActivityRecyvlerviewBinding, NewFirendViewModel>() {

    override fun getBaseViewModel() = NewFirendViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        initTitle("新的朋友")

        viewModel?.let {
            it.bind=binding
            it.initViewmodel()
        }
    }

}