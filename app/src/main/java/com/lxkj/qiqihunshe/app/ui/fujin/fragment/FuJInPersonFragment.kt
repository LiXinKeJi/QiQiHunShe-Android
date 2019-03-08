package com.lxkj.qiqihunshe.app.ui.fujin.fragment

import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.fujin.model.ValuesModel
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.FuJInPersonViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import kotlinx.android.synthetic.main.activity_xrecyclerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/26
 */
class FuJInPersonFragment : BaseFragment<ActivityXrecyclerviewBinding, FuJInPersonViewModel>() {


    override fun getBaseViewModel() = FuJInPersonViewModel()

    override fun getLayoutId() = R.layout.activity_xrecyclerview

    override fun init() {
        EventBus.getDefault().register(this)
        viewModel?.let {
            it.bind = binding
            it.init()
        }
    }

    override fun loadData() {
        include.visibility = View.GONE
    }

    @Subscribe
    fun onEvent(next: String) {
        if(next=="screen"){
            ToastUtil.showToast("筛选")
        }
    }

    @Subscribe
    fun onEvent(model: ValuesModel) {
//        ToastUtil.showTopSnackBar(activity!!, Gson().toJson(model))
        viewModel?.age = model.min + "-" + model.max
        viewModel?.sex = model.sex
        xRecyclerView.refresh()
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}