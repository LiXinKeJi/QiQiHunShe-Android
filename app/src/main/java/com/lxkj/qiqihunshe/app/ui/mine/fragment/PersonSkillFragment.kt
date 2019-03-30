package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.content.Intent
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PersonSkillViewModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentPersonSkillBinding
import kotlinx.android.synthetic.main.fragment_person_skill.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * 才艺
 * Created by Slingge on 2019/2/21
 */
class PersonSkillFragment : BaseFragment<FragmentPersonSkillBinding, PersonSkillViewModel>() {


    override fun getBaseViewModel() = PersonSkillViewModel()

    override fun getLayoutId() = R.layout.fragment_person_skill

    override fun init() {
        viewModel?.let {
            include.visibility = View.GONE
            binding.viewmodel = it
            it.userId = arguments!!.getString("id")
            it.bind = binding
            it.initViewModel()

            it.adapter.setLoadMore {
                viewModel?.let {
                    it.page++
                    if (it.page > it.totalPage) {
                        return@setLoadMore
                    }
                    include.visibility = View.VISIBLE
                    it.getSkill().bindLifeCycle(this).subscribe({
                        include.visibility = View.GONE
                    }, { toastFailure(it) })
                }
            }
        }
    }

    override fun loadData() {
        EventBus.getDefault().register(this)
        viewModel!!.getSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        if (model.cmd == EventBusCmd.DelSkill) {//删除才艺
            ToastUtil.showToast(model.res)
            viewModel!!.removeItem(model.res.toInt())
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