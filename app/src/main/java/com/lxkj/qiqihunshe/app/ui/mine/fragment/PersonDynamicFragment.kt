package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.content.Intent
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonDynamicBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDynamicFragment : BaseFragment<FragmentPersonDynamicBinding, PersonDynamicViewModel>() {

    override fun getBaseViewModel() = PersonDynamicViewModel()

    override fun getLayoutId() = R.layout.fragment_person_dynamic

    override fun init() {
        viewModel?.let {
            binding.viewmodel = it
            it.userId = arguments!!.getString("id")
            it.bind = binding
            it.initViewModel()
            it.adapter.setLoadMore {
                viewModel?.let {
                    it.page++
                    if (it.page > it.totalpage) {
                        return@setLoadMore
                    }
                    it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }
    }

    override fun loadData() {
        EventBus.getDefault().register(this)
        viewModel!!.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        when (model.cmd) {
            EventBusCmd.DianZan -> {
                ToastUtil.showToast(model.res.toString())
                viewModel!!.zan(model.res.toInt()).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
            EventBusCmd.JuBao -> {
                ReportDialog1.show(activity!!, object : ReportDialog1.ReportCallBack {
                    override fun report(report: String) {
                        viewModel!!.jubao(report, model.res.toInt()).bindLifeCycle(this@PersonDynamicFragment)
                            .subscribe({}, { toastFailure(it) })
                    }
                })
            }
            EventBusCmd.dashang -> {
                DaShangDialog.show(activity!!, object : DaShangDialog.DaShangCallBack {
                    override fun dashang(money: String) {
                        viewModel!!.dashang(money, model.res.toInt()).bindLifeCycle(this@PersonDynamicFragment)
                            .subscribe({ }, { toastFailure(it) })
                    }
                })
            }
            EventBusCmd.fenxaing -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            if (data.getStringExtra("cmd") == "add") {
                viewModel?.let {
                    it.adapter.flag = 2
                    it.page = 1
                    it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}