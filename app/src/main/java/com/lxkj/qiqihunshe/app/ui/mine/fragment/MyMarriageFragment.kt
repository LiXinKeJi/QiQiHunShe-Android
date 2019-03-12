package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyMarriageViewModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyProblemViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的情感-我的征婚(与我的空间-我的邀约item相同)
 * Created by Slingge on 2019/2/26
 */
class MyMarriageFragment : BaseFragment<ActivityRecyvlerviewBinding, MyMarriageViewModel>() {

    override fun getBaseViewModel() = MyMarriageViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview
    override fun init() {
        EventBus.getDefault().register(this)
        include.visibility = View.GONE
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
            it.adapter.setLoadMore {
                it.page++
                if (it.page <= it.totalPage) {
                    it.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }
    }

    override fun loadData() {
        viewModel!!.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        if (model.cmd == "add") {
            viewModel?.let {
                it.page = 1
                it.adapter.flag = 2
                it.getYaoyue().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        } else if (model.cmd == EventBusCmd.DelInvitation) {//删除邀约
            viewModel?.let {
                it.DelInvitation(model.res.toInt()).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}