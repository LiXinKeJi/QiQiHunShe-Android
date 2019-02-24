package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AgreementViewModel

/**
 * Created by Slingge on 2019/2/19
 */
class AgreementActivity:BaseActivity<com.lxkj.qiqihunshe.databinding.ActivityAgreementBinding,AgreementViewModel>() {
    override fun getBaseViewModel()= AgreementViewModel ()

    override fun getLayoutId()= R.layout.activity_agreement

    override fun init() {
        initTitle("服务协议")
    }



}