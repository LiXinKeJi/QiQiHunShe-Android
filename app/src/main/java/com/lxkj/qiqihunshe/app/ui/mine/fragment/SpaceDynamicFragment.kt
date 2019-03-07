package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SpaceDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceDynamicFragment : BaseFragment<ActivityRecyvlerviewBinding, SpaceDynamicViewModel>() {

    override fun getBaseViewModel() = SpaceDynamicViewModel()

    override fun getLayoutId()= R.layout.activity_recyvlerview

    override fun init() {
        include.visibility= View.GONE

        viewModel?.let {
            recycler.setPadding(0,12,0,0)
            it.bind=binding
            it.initViewModel()
        }

    }

    override fun loadData() {
    }


}