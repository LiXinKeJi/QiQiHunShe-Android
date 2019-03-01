package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.XiangShiViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/28
 */
class XiangShiFragment : BaseFragment<ActivityRecyvlerviewBinding, XiangShiViewModel>() {


    override fun getBaseViewModel() = XiangShiViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
    include.visibility= View.GONE
        viewModel?.let {
            it.bind = binding
            it.initViewmodel()
        }

    }

    override fun loadData() {
    }


}