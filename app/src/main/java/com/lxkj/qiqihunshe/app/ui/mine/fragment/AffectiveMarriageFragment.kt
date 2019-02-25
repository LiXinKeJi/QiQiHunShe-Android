package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AffectiveMarriageViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

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