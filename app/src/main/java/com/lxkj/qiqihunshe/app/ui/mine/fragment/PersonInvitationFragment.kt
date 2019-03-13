package com.lxkj.qiqihunshe.app.ui.mine.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonInvitationFragmentViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonInvitationBinding

/**
 * 邀约
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationFragment : BaseFragment<FragmentPersonInvitationBinding, PersonInvitationFragmentViewModel>() {

    override fun getBaseViewModel() = PersonInvitationFragmentViewModel()

    override fun getLayoutId() = R.layout.fragment_person_invitation

    override fun init() {
        viewModel?.let {
            binding.viewmodel = it
            it.userId = arguments!!.getString("id")
            it.bind = binding
            it.initViewModel()
            it.adapter.setLoadMore {
                it.page++
                if (it.page > it.totalPage) {
                    return@setLoadMore
                }
                it.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }

    override fun loadData() {
        viewModel!!.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


}