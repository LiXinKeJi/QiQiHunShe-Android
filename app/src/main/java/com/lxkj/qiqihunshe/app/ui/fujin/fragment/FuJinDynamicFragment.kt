package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseDynamicActivity
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
        }
        fab.visibility = View.VISIBLE
        fab.attachToRecyclerView(xRecyclerView)
        fab.setOnClickListener {
            MyApplication.openActivity(activity, ReleaseDynamicActivity::class.java)
        }
    }

    override fun loadData() {


    }


    override fun onDestroy() {
        super.onDestroy()
        DaShangDialog.diss()
        DaShangAfterDialog.diss()
    }

}