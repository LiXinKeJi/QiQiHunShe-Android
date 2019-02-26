package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinDynamicFragment : BaseFragment<ActivityRecyvlerviewBinding, FuJinDynamicViewModel>() {


    override fun getBaseViewModel() = FuJinDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview
    override fun init() {

    }

    override fun loadData() {
    }
}