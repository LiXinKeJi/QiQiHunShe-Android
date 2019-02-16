package com.lxkj.qiqihunshe.app.ui.shouye

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.FuJinViewModel
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.ShouYeViewModel
import com.lxkj.qiqihunshe.databinding.FragmentShouyeBinding

/**
 * Created by Slingge on 2019/2/16
 */
class ShouYeFragment : BaseFragment<FragmentShouyeBinding, ShouYeViewModel>() {


    override fun getBaseViewModel() = ShouYeViewModel()

    override fun getLayoutId() = R.layout.fragment_shouye

    override fun init() {


    }

    override fun loadData() {
    }


}