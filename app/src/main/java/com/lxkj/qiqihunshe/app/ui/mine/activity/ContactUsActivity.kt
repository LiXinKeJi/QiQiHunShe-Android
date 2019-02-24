package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ContactUsViewModel
import com.lxkj.qiqihunshe.databinding.ActivityContactusBinding

/**
 * Created by Slingge on 2019/2/19
 */
class ContactUsActivity : BaseActivity<ActivityContactusBinding, ContactUsViewModel>() {


    override fun getBaseViewModel() = ContactUsViewModel()

    override fun getLayoutId() = R.layout.activity_contactus

    override fun init() {
        initTitle("联系我们")

        viewModel?.let {
            binding.viewmodel = it

        }

    }


}