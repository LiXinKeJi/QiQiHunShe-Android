package com.lxkj.qiqihunshe.app.ui.quyu

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.QuYuViewModel
import com.lxkj.qiqihunshe.databinding.FragmentQuyuBinding

/**
 * Created by Slingge on 2019/2/16
 */
class QuYuFragment : BaseFragment<FragmentQuyuBinding, QuYuViewModel>() {


    override fun getBaseViewModel() = QuYuViewModel()

    override fun getLayoutId() = R.layout.fragment_quyu

    override fun init() {


    }

    override fun loadData() {
    }


}