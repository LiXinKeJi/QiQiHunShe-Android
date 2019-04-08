package com.lxkj.qiqihunshe.app.ui.entrance

import android.view.WindowManager
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.WelComeViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityWelcomeBinding
import kotlinx.android.synthetic.main.activity_welcome.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/4/4
 */
class WelComeActivity : BaseActivity<ActivityWelcomeBinding, WelComeViewModel>() {

    override fun getBaseViewModel() = WelComeViewModel()
    override fun getLayoutId() = R.layout.activity_welcome

    override fun init() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        viewModel?.let {
            it.bind = binding
            it.supportFragmentManager = supportFragmentManager
            it.init()
        }
    }


}