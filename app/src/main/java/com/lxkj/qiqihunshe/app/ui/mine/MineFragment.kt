package com.lxkj.qiqihunshe.app.ui.shouye

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.MineViewModel
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.QuYuViewModel
import com.lxkj.qiqihunshe.databinding.FragmentMineBinding

/**
 * Created by Slingge on 2019/2/16
 */
class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>() {


    override fun getBaseViewModel() = MineViewModel()

    override fun getLayoutId() = R.layout.fragment_mine

    override fun init() {


    }

    override fun loadData() {
    }


}