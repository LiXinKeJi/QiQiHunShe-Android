package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.content.Intent
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.activity.ChargingSetUpActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.SeenSkillActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SpaceSkillViewModel
import com.lxkj.qiqihunshe.app.ui.model.DelDynamicModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.databinding.FragmentSpaceSkillBinding
import kotlinx.android.synthetic.main.fragment_space_skill.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 我的空间- 我的邀约
 * Created by Slingge on 2019/2/25
 */
class SpaceSkillFragment : BaseFragment<FragmentSpaceSkillBinding, SpaceSkillViewModel>() {
    override fun getBaseViewModel() = SpaceSkillViewModel()

    override fun getLayoutId() = R.layout.fragment_space_skill

    override fun init() {
        EventBus.getDefault().register(this)
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
            it.getSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })

            it.adapter.setLoadMore {
                viewModel!!.page++
                it.getSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }

        refresh.setOnRefreshListener {
            viewModel!!.page = 1
            viewModel!!.getSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

        tv_setup.setOnClickListener {
            MyApplication.openActivity(activity, ChargingSetUpActivity::class.java)
        }
        tv_see.setOnClickListener {
            MyApplication.openActivity(activity, SeenSkillActivity::class.java)
        }

    }

    override fun loadData() {
    }


    @Subscribe
    fun onEvent(model: DelDynamicModel) {
        if (model.cmd == EventBusCmd.DelSkill) {//删除才艺
            viewModel!!.DelSkill(model.position).bindLifeCycle(this).subscribe({}, { it })
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        if (resultCode == 1 && requestCode == 1) {
            if (data.getStringExtra("cmd") == "add") {
                viewModel!!.page = 1
                viewModel!!.getSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }else{
                viewModel!!.removeItem(data.getIntExtra("position", -1))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}