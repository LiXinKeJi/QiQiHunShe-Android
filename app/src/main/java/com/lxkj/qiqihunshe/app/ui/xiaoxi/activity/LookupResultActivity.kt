package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupResultViewModel
import com.lxkj.qiqihunshe.databinding.ActivityLookupResultBinding

/**
 * Created by Slingge on 2019/3/1
 */
class LookupResultActivity :
    BaseActivity<ActivityLookupResultBinding, LookupResultViewModel>() {


    override fun getBaseViewModel()= LookupResultViewModel ()

    override fun getLayoutId()= R.layout.activity_lookup_result

    override fun init() {
        initTitle(intent.getStringExtra("title"))

        viewModel?.let {
            it.bind=binding
            it.initViewmodel()
        }
    }

}