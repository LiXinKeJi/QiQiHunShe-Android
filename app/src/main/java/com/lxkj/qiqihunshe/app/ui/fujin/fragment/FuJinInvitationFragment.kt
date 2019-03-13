package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJinInvitationViewModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReleaseInvitationTypeActivity
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


}