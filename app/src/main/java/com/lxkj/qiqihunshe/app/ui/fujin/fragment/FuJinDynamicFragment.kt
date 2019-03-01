package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseDynamicActivity
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinDynamicFragment : BaseFragment<ActivityRecyvlerviewBinding, FuJinDynamicViewModel>() {


    override fun getBaseViewModel() = FuJinDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview
    override fun init() {
        include.visibility = View.GONE

        fab.visibility = View.VISIBLE
        fab.attachToRecyclerView(recycler)
        fab.setOnClickListener {
            MyApplication.openActivity(activity, ReleaseDynamicActivity::class.java)
        }
    }

    override fun loadData() {

        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        DaShangDialog.diss()
        DaShangAfterDialog.diss()
    }

}