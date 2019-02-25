package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ChargingSetUpViewModel

/**
 * Created by Slingge on 2019/2/25
 */
class ChargingSetUpActivity :BaseActivity<com.lxkj.qiqihunshe.databinding.ActivityChargingSetupBinding,ChargingSetUpViewModel>(){
    override fun getBaseViewModel()=ChargingSetUpViewModel()

    override fun getLayoutId()= R.layout.activity_charging_setup

    override fun init() {
      initTitle("收费设置")


    }


}