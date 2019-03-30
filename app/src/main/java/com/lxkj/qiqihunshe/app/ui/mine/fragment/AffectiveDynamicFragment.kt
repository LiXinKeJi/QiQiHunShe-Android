package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AffectiveDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.ShareUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/25
 */
class AffectiveDynamicFragment : BaseFragment<ActivityRecyvlerviewBinding, AffectiveDynamicViewModel>() {


    override fun getBaseViewModel() = AffectiveDynamicViewModel()


    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        include.visibility = View.GONE
        fab.visibility = View.VISIBLE
        fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("flag", 1)
            MyApplication.openActivityForResult(activity, ReleaseDynamicActivity::class.java, bundle, 0)
        }
        fab.attachToRecyclerView(recycler)

        refresh.setOnRefreshListener {
            viewModel?.let {
                it.page = 1
                it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }

    }

    override fun loadData() {
        EventBus.getDefault().register(this)
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
            it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

    }

    @Subscribe
    fun onEvent(model: EventCmdModel) {
        when (model.cmd) {
            EventBusCmd.JuBao -> {
                ReportDialog1.getReportList(activity!!, "2", object : ReportDialog1.ReportCallBack {
                    override fun report(report: String) {
                        viewModel!!.jubao(report, model.res.toInt()).bindLifeCycle(this@AffectiveDynamicFragment)
                            .subscribe({}, { toastFailure(it) })
                    }
                })
            }
            EventBusCmd.dashang -> {
                DaShangDialog.show(activity!!, object : DaShangDialog.DaShangCallBack {
                    override fun dashang(money: String) {
                        viewModel!!.dashang(money, model.res.toInt()).bindLifeCycle(this@AffectiveDynamicFragment)
                            .subscribe({ }, { toastFailure(it) })
                    }
                })
            }
            EventBusCmd.fenxaing -> {
                ShareUtil.share(activity!!)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0 && resultCode == 303) {
            DaShangAfterDialog.show(activity!!)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        ReportDialog1.diss()
        DaShangAfterDialog.diss()
    }


}