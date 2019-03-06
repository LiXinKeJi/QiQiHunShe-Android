package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.entrance.model.ForgetPassModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.VerificationPhoneViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityVerificationPhoneBinding
import kotlinx.android.synthetic.main.activity_verification_phone.*

/**
 * Created by Slingge on 2019/2/20
 */
class VerificationPhoneActivity : BaseActivity<ActivityVerificationPhoneBinding, VerificationPhoneViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = VerificationPhoneViewModel()

    override fun getLayoutId() = R.layout.activity_verification_phone

    private val model by lazy { ForgetPassModel() }


    override fun init() {
        initTitle("手机号验证")

        tv_getcode.setOnClickListener(this)
        tv_enter.setOnClickListener(this)

        viewModel?.let {
            binding.model = model
            binding.viewmodel = it
            it.bind = binding
        }


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_getcode -> {
                viewModel?.getCode()
            }
            R.id.tv_enter -> {
                model.notif()
                ToastUtil.showToast(model.code)

                viewModel?.jump()
            }
        }
    }


}