package com.lxkj.qiqihunshe.app.ui.mine.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonInvitationFragmentViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.databinding.FragmentPersonInvitationBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 邀约
 * Created by Slingge on 2019/2/21
 */
class PersonInvitationFragment : BaseFragment<FragmentPersonInvitationBinding, PersonInvitationFragmentViewModel>() {

    override fun getBaseViewModel() = PersonInvitationFragmentViewModel()

    override fun getLayoutId() = R.layout.fragment_person_invitation

    override fun init() {
        EventBus.getDefault().register(this)
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


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        if (!isVisibleToUser) {//不显示此fragment不执行
            return
        }
        when (model.cmd) {
            EventBusCmd.JuBao -> {
                ReportDialog1.show(activity!!, object : ReportDialog1.ReportCallBack {
                    override fun report(report: String) {
                        viewModel!!.jubao(report, model.res.toInt()).bindLifeCycle(this@PersonInvitationFragment)
                            .subscribe({}, { toastFailure(it) })
                    }
                })
            }
        }
    }


    override fun loadData() {
        viewModel!!.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}