package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ActivityRecordViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import kotlinx.android.synthetic.main.activity.*

/**
 * 活动记录
 * Created by Slingge on 2019/2/22
 */
class ActivityRecordFragment : BaseFragment<ActivityXrecyclerviewBinding, ActivityRecordViewModel>() {


    override fun getBaseViewModel() = ActivityRecordViewModel()

    override fun getLayoutId() = R.layout.activity_xrecyclerview


    override fun init() {
        include.visibility= View.GONE

        viewModel?.let {
            it.bind=binding
            viewModel?.init()
        }
    }

    override fun loadData() {

    }

}