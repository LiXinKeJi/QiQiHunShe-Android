package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import android.view.ViewGroup
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SpaceDynamicViewModel
import com.lxkj.qiqihunshe.app.ui.model.DelDynamicModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceDynamicFragment : BaseFragment<ActivityRecyvlerviewBinding, SpaceDynamicViewModel>() {

    override fun getBaseViewModel() = SpaceDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        EventBus.getDefault().register(this)
        include.visibility = View.GONE

        recycler.setPadding(0, 60, 0, 0)

        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }

        refresh.setOnRefreshListener {
            viewModel?.let {
                it.page = 1
                it.getMyDynamic().bindLifeCycle(this)
                    .subscribe({}, { toastFailure(it) })
            }
        }

    }

    override fun loadData() {
        viewModel!!.getMyDynamic().bindLifeCycle(this)
            .subscribe({}, { toastFailure(it) })
    }


    @Subscribe
    fun onEvent(model: DelDynamicModel) {
        if (model.cmd == EventBusCmd.DelDynamic) {//删除动态
            viewModel!!.DelDynamuc(model.position).bindLifeCycle(this).subscribe({}, { it })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}