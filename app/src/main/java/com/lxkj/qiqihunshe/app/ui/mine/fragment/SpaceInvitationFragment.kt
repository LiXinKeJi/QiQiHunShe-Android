package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SpaceDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SpaceInvitationViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * 我的空间- 我的才艺
 * Created by Slingge on 2019/2/25
 */
class SpaceInvitationFragment : BaseFragment<ActivityRecyvlerviewBinding, SpaceInvitationViewModel>() {
    override fun getBaseViewModel() = SpaceInvitationViewModel()

    override fun getLayoutId()= R.layout.activity_recyvlerview

    override fun init() {
        include.visibility= View.GONE


    }

    override fun loadData() {
        viewModel?.let{
            it.bind=binding
            it.initViewModel()
        }


    }


}