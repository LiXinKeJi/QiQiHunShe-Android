package com.lxkj.qiqihunshe.app.ui.shouye.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.SetupProblemViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivitySetupProblemBinding
import kotlinx.android.synthetic.main.activity_setup_problem.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/26
 */
class SetupProblemActivity : BaseActivity<ActivitySetupProblemBinding, SetupProblemViewModel>() {


    override fun getBaseViewModel() = SetupProblemViewModel()

    override fun getLayoutId() = R.layout.activity_setup_problem


    override fun init() {
        initTitle("设置问题")
        EventBus.getDefault().register(this)
        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.init()
            it.getQuestion().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

        tv_next.setOnClickListener {
            if (viewModel!!.ids.isEmpty()) {
                ToastUtil.showTopSnackBar(this, "请选择答案")
                return@setOnClickListener
            }
            viewModel!!.Submission().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }


    }


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        viewModel?.let {
            it.setId(model.cmd.toBoolean(), model.res.toInt())
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}