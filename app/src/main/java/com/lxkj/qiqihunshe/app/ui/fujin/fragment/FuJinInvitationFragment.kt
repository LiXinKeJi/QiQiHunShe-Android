package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinInvitationViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseInvitationTypeActivity
import com.lxkj.qiqihunshe.databinding.FragmentFujinInvitationBinding
import kotlinx.android.synthetic.main.fragment_fujin_invitation.*

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinInvitationFragment : BaseFragment<FragmentFujinInvitationBinding, FuJinInvitationViewModel>() {

    override fun getBaseViewModel() = FuJinInvitationViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin_invitation

    override fun init() {

        fab.attachToRecyclerView(recycler)
        fab.setOnClickListener {
            MyApplication.openActivity(activity, ReleaseInvitationTypeActivity::class.java)
        }

        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }

    }

    override fun loadData() {
    }

}