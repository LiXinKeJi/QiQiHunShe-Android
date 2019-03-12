package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.databinding.ActivitySecuritySetupBinding
import kotlinx.android.synthetic.main.activity_security_setup.*

/**
 * Created by Slingge on 2019/2/20
 */
class SecuritySetUpActivity : BaseActivity<ActivitySecuritySetupBinding, BaseViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = BaseViewModel()

    override fun getLayoutId() = R.layout.activity_security_setup

    override fun init() {
        initTitle("安全设置")

        tv_paypass.setOnClickListener(this)
        tv_sginpass.setOnClickListener(this)
        tv_replacephont.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_paypass -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivity(this, VerificationPhoneActivity::class.java, bundle)
            }
            R.id.tv_sginpass -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(this, VerificationPhoneActivity::class.java, bundle)
            }
            R.id.tv_replacephont -> {
                MyApplication.openActivity(this, AnswerProblemActivity::class.java)
            }
        }
    }


}