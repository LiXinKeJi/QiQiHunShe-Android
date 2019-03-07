package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.CheckInViewModel
import com.lxkj.qiqihunshe.databinding.ActivityCheckinBinding

/**
 * Created by Slingge on 2019/2/21
 */
class CheckInActivity : BaseActivity<ActivityCheckinBinding, CheckInViewModel>() {


    override fun getBaseViewModel() = CheckInViewModel()

    override fun getLayoutId() = R.layout.activity_checkin

    override fun init() {
        initTitle("签到")

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.checkIn().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

    }

}