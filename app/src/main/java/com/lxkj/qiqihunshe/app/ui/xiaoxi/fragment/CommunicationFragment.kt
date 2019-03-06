package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.NewFirendActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.CommunicationViewModel
import com.lxkj.qiqihunshe.databinding.FraCommunicationBinding
import kotlinx.android.synthetic.main.fra_communication.*

/**
 * 消息-通讯
 * Created by Slingge on 2019/2/28
 */
class CommunicationFragment : BaseFragment<FraCommunicationBinding, CommunicationViewModel>() {
    override fun getBaseViewModel() = CommunicationViewModel()
    override fun getLayoutId() = R.layout.fra_communication


    override fun init() {
        include.visibility = View.GONE
        viewModel?.bind = binding
        viewModel?.init()
        llNew.setOnClickListener {
            MyApplication.openActivity(context, NewFirendActivity::class.java)
        }
    }

    override fun loadData() {

    }


}