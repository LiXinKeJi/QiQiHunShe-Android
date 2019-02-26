package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJInPersonViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJInPersonFragment : BaseFragment<ActivityRecyvlerviewBinding, FuJInPersonViewModel>() {


    override fun getBaseViewModel() = FuJInPersonViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        viewModel?.let {
            it.bind=binding
            it.initViewModel()
        }
    }

    override fun loadData() {
    }
}