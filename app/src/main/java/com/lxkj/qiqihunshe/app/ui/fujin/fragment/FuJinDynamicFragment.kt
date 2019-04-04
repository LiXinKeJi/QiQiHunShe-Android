package com.lxkj.qiqihunshe.app.ui.fujin.fragment

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
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.NearDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseDynamicActivity
import com.lxkj.qiqihunshe.app.util.ShareUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import kotlinx.android.synthetic.main.activity_xrecyclerview.*

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinDynamicFragment : BaseFragment<ActivityXrecyclerviewBinding, FuJinDynamicViewModel>() {


    override fun getBaseViewModel() = FuJinDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_xrecyclerview
    override fun init() {
        include.visibility = View.GONE
        viewModel?.let {
            it.bind = binding
            it.init()

            it.adapter?.setOnItemClickListener(object : NearDynamicAdapter.OnItemClickListener {
                override fun OnItemClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putSerializable("bean", it.list[position])
                    MyApplication.openActivity(activity, MyDynamicActivity::class.java, bundle)
                }

                override fun zanClick(position: Int) {
                    viewModel!!.zan(position).bindLifeCycle(this@FuJinDynamicFragment)
                        .subscribe({}, { toastFailure(it) })
                }

                override fun shareClick(position: Int) {//分享
                    ShareUtil.share(activity!!)
                }

                override fun daShnangClick(position: Int) {
                    DaShangDialog.show(activity!!, object : DaShangDialog.DaShangCallBack {
                        override fun dashang(money: String) {
                            viewModel!!.dashang(money, position).bindLifeCycle(this@FuJinDynamicFragment)
                                .subscribe({}, { toastFailure(it) })
                        }
                    })
                }

                override fun juBaoClick(position: Int) {
                    ReportDialog1.getReportList(activity!!, "2", object : ReportDialog1.ReportCallBack {
                        override fun report(report: String) {
                            viewModel!!.jubao(report, position).bindLifeCycle(this@FuJinDynamicFragment)
                                .subscribe({}, { toastFailure(it) })
                        }
                    })
                }
            })
        }
        fab.visibility = View.VISIBLE
        fab.attachToRecyclerView(xRecyclerView)
        fab.setOnClickListener {
            if(!StaticUtil.isRealNameAuth(activity!!)){
                return@setOnClickListener
            }

            // flag = -1//0普通动态，1情感动态
            val bundle = Bundle()
            bundle.putInt("flag", 0)
            MyApplication.openActivity(activity, ReleaseDynamicActivity::class.java, bundle)
        }
    }

    override fun loadData() {
        viewModel!!.getList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 201 && resultCode == 303) {
            DaShangAfterDialog.show(activity!!)
        }
    }


    override fun onPause() {
        super.onPause()
        DaShangDialog.diss()
        DaShangAfterDialog.diss()
    }


}