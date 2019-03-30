package com.lxkj.qiqihunshe.app.ui.entrance

import android.view.WindowManager
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.StartupPageViewModel
import com.lxkj.qiqihunshe.databinding.ActivityStartupPageBinding

/**
 * Created by Slingge on 2019/3/25
 */
class StartupPageActivity : BaseActivity<ActivityStartupPageBinding, StartupPageViewModel>() {


    override fun getBaseViewModel() = StartupPageViewModel()

    override fun getLayoutId() = R.layout.activity_startup_page


    override fun init() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        viewModel?.let {
            it.timer.start()
        }
    }


}