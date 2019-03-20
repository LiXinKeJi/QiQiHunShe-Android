package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.NearInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinInvitationViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseInvitationTypeActivity
import com.lxkj.qiqihunshe.app.util.SeePhotoViewUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentFujinInvitationBinding
import kotlinx.android.synthetic.main.fragment_fujin_invitation.*

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinInvitationFragment : BaseFragment<FragmentFujinInvitationBinding, FuJinInvitationViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = FuJinInvitationViewModel()

    override fun getLayoutId() = R.layout.fragment_fujin_invitation

    override fun init() {

        fab.attachToRecyclerView(xRecyclerView)
        fab.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("type", 0)
            MyApplication.openActivity(activity, ReleaseInvitationTypeActivity::class.java, bundle)
        }

        viewModel?.let {
            it.bind = binding
            it.init()

            it.adapter!!.setOnItemClickListener(object : NearInvitationAdapter.OnItemClickListener {
                override fun seePhotoonClick(position: Int, count: Int) {
                    SeePhotoViewUtil.toPhotoView(activity, it.list[position].image, count)
                }

                override fun ReportonClick(position: Int) {
                    ReportDialog1.getReportList(activity!!, "3", object : ReportDialog1.ReportCallBack {
                        override fun report(report: String) {
                            it.yaoyueReport(report, position).bindLifeCycle(this@FuJinInvitationFragment)
                                .subscribe({}, { toastFailure(it) })
                        }
                    })
                }

                override fun OnItemClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putString("id", it.list[position].yaoyueId)
                    MyApplication.openActivity(activity, PersonInvitationDetailsActivity::class.java, bundle)
                }
            })
        }

        rba.setOnClickListener(this)
        tv_food.setOnClickListener(this)
        tv_movie.setOnClickListener(this)
        tv_travel.setOnClickListener(this)
        tv_other.setOnClickListener(this)
        xRecyclerView.refresh()
    }

    override fun loadData() {
        viewModel?.getList()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.rba -> {
                viewModel!!.typeId = ""
                xRecyclerView.refresh()
            }
            R.id.tv_food -> {
                viewModel!!.typeId = "1"
                xRecyclerView.refresh()
            }
            R.id.tv_movie -> {
                viewModel!!.typeId = "2"
                xRecyclerView.refresh()
            }
            R.id.tv_travel -> {
                viewModel!!.typeId = "3"
                xRecyclerView.refresh()
            }
            R.id.tv_other -> {
                viewModel!!.typeId = "4"
                xRecyclerView.refresh()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ReportDialog1.diss()
    }

}