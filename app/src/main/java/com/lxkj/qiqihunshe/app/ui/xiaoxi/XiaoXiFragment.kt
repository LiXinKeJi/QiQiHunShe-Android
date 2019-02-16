package com.lxkj.qiqihunshe.app.ui.shouye

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.XiaoXiViewModel
import com.lxkj.qiqihunshe.databinding.FragmentXiaoxiBinding

/**
 * Created by Slingge on 2019/2/16
 */
class XiaoXiFragment : BaseFragment<FragmentXiaoxiBinding, XiaoXiViewModel>() {


    override fun getBaseViewModel() = XiaoXiViewModel()

    override fun getLayoutId() = R.layout.fragment_xiaoxi

    override fun init() {


    }

    override fun loadData() {
    }


}