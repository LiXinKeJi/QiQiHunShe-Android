package com.lxkj.qiqihunshe.app.ui.shouye

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.FuJinViewModel
import com.lxkj.qiqihunshe.databinding.FragmentFujinBinding
import com.lxkj.qiqihunshe.databinding.FragmentShouyeBinding

/**
 * Created by Slingge on 2019/2/16
 */
class FuJinFragment : BaseFragment<FragmentFujinBinding, FuJinViewModel>() {


    override fun getBaseViewModel() = FuJinViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin

    override fun init() {


    }

    override fun loadData() {
    }


}