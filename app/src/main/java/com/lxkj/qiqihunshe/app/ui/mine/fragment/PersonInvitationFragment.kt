package com.lxkj.qiqihunshe.app.ui.mine.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonInvitationViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonInvitationBinding

/**
 * 邀约
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationFragment : BaseFragment<FragmentPersonInvitationBinding, PersonInvitationViewModel>() {


    override fun getBaseViewModel() = PersonInvitationViewModel()

    override fun getLayoutId() = R.layout.fragment_person_invitation

    override fun init() {
    }

    override fun loadData() {
        ToastUtil.showToast("邀约")
        viewModel?.let {
            binding.viewmodel=it
            it.bind = binding
            it.initViewModel()
        }


    }


}