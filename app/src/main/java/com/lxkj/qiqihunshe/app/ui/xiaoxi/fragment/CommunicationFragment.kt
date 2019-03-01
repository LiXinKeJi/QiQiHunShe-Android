package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.CommunicationViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/28
 */
class CommunicationFragment : BaseFragment<ActivityRecyvlerviewBinding, CommunicationViewModel>() {



    override fun getBaseViewModel()= CommunicationViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        include.visibility= View.GONE
    }

    override fun loadData() {

    }



}