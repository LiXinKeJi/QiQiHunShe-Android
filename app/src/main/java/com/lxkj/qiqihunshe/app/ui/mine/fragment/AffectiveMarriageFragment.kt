package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseInvitationTypeActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AffectiveMarriageViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 情感征婚
 * Created by Slingge on 2019/2/25
 */
class AffectiveMarriageFragment : BaseFragment<ActivityRecyvlerviewBinding, AffectiveMarriageViewModel>() {

    override fun getBaseViewModel() = AffectiveMarriageViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        include.visibility = View.GONE
        fab.visibility = View.VISIBLE
        fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", 1)
            MyApplication.openActivity(activity, ReleaseInvitationTypeActivity::class.java, bundle)
        }
        fab.attachToRecyclerView(recycler)
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }

        refresh.setOnRefreshListener {
            viewModel?.let {
                it.page = 1
                it.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }

    }

    override fun loadData() {
        EventBus.getDefault().register(this)
        viewModel?.let {
            it.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }

    @Subscribe
    fun onEvent(model: EventCmdModel) {
        if (model.cmd == EventBusCmd.JuBao) {//举报
            ReportDialog1.getReportList(activity!!, "3",object : ReportDialog1.ReportCallBack {
                override fun report(report: String) {
                    viewModel!!.jubao(report, model.res.toInt()).bindLifeCycle(this@AffectiveMarriageFragment)
                        .subscribe({}, { toastFailure(it) })
                }
            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}