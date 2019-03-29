package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
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


    override fun init() {
        initTitle("手机号验证")

        tv_getcode.setOnClickListener(this)
        tv_enter.setOnClickListener(this)

        viewModel?.let {
            binding.model = it.model
            binding.viewmodel = it
            it.flag = intent.getIntExtra("flag", 0)
            it.bind = binding
            it.init()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_getcode -> {
                viewModel!!.model.notif()
                if (TextUtils.isEmpty(viewModel!!.model.phone)) {
                    ToastUtil.showTopSnackBar(this, "请输入手机号")
                    return
                }
                viewModel!!.getCode().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
            R.id.tv_enter -> {
                viewModel?.let {
                    it.model.notif()
                    if (TextUtils.isEmpty(it.model.code)) {
                        ToastUtil.showTopSnackBar(this, "请输入验证码")
                        return
                    }
                    it.jump()
                }

            }
        }
    }


}