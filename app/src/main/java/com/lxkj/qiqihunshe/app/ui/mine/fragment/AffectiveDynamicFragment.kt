package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AffectiveDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

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
            MyApplication.openActivity(activity, ReleaseDynamicActivity::class.java)
        }
        fab.attachToRecyclerView(recycler)
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }
    }

    override fun loadData() {
    }
}