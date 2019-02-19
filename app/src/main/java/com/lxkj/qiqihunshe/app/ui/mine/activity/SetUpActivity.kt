package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SetUpViewModel
import com.lxkj.qiqihunshe.app.util.DataCleanManager
import com.lxkj.qiqihunshe.databinding.ActivitySetupBinding
import kotlinx.android.synthetic.main.activity_setup.*

/**
 * Created by Slingge on 2019/2/19
 */
class SetUpActivity : BaseActivity<ActivitySetupBinding, SetUpViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = SetUpViewModel()

    override fun getLayoutId() = R.layout.activity_setup

    override fun init() {
        WhiteStatusBar()
        initTitle("设置")

        viewModel?.let {
            binding.viewmodel = it
        }

        tv_contact.setOnClickListener(this)
        tv_agreement.setOnClickListener(this)
        rl_clear.setOnClickListener(this)

        tv_cache.text = DataCleanManager.getTotalCacheSize(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_clear -> {
                DataCleanManager.clearAllCache(this)
                tv_cache.text = "0.0MB"
            }
            R.id.tv_agreement -> {
                MyApplication.openActivity(this, AgreementActivity::class.java)
            }
            R.id.tv_contact -> {
                MyApplication.openActivity(this, ContactUsActivity::class.java)
            }
        }
    }


}