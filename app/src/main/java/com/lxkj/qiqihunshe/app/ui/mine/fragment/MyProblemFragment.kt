package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.content.Intent
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyProblemViewModel
import com.lxkj.qiqihunshe.app.ui.model.DelDynamicModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的问题
 * Created by Slingge on 2019/2/26
 */
class MyProblemFragment : BaseFragment<ActivityRecyvlerviewBinding, MyProblemViewModel>() {

    override fun getBaseViewModel() = MyProblemViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview
    override fun init() {
        EventBus.getDefault().register(this)
        include.visibility = View.GONE
        recycler.setPadding(0, 60, 0, 0)
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
            it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })

            it.adapter.setLoadMore {
                it.page++
                if(it.page<=it.totalPage){
                    it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }

        refresh.setOnRefreshListener {
            viewModel?.let {
                it.page=1
                it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }


    @Subscribe
    fun onEvent(model: DelDynamicModel) {
        if (model.cmd == EventBusCmd.DelDynamic) {//删除动态
            viewModel!!.DelDynamuc(model.position).bindLifeCycle(this).subscribe({}, { it })
        }
    }

    override fun loadData() {
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        if (resultCode == 0 && requestCode == 0) {
            if (data.getStringExtra("cmd") == "add") {
                viewModel?.let {
                    it.page = 1
                    it.getMyDynamic().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            } else {
                viewModel!!.removeItem(data.getIntExtra("position", -1))
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}