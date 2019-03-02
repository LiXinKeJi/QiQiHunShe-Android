package com.lxkj.qiqihunshe.app.ui.map.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.map.viewmodel.ChooseAddressViewModel
import com.lxkj.qiqihunshe.databinding.ActivityChooseAddressBinding

/**
 * Created by kxn on 2019/3/2 0002.
 * 选择位置信息
 */
class ChooseAddressActivity : BaseActivity<ActivityChooseAddressBinding,ChooseAddressViewModel>(){
    override fun getBaseViewModel(): ChooseAddressViewModel = ChooseAddressViewModel()

    override fun getLayoutId(): Int = R.layout.activity_choose_address

    override fun init() {
    }

}