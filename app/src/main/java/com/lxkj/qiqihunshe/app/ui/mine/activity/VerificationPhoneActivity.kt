package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.TextUtils
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
   var code = ""

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
                if(TextUtils.isEmpty(et_phone.text)){
                    ToastUtil.showToast(getString(R.string.phone_isnot_null))
                    return
                }
                viewModel?.getCode()
            }
            R.id.tv_enter -> {
                model.notif()
               // ToastUtil.showToast(model.code)
                if(TextUtils.isEmpty(et_phone.text)){
                    ToastUtil.showToast(getString(R.string.phone_isnot_null))
                    return
                }

                if(TextUtils.isEmpty(et_code.text)){
                    ToastUtil.showToast(getString(R.string.code_isnot_null))
                    return
                }

                if(!TextUtils.equals(et_code.text,viewModel!!.CODE)){
                    ToastUtil.showToast(getString(R.string.code_write_wrong))
                    return
                }
                viewModel?.jump(et_phone.text.toString(),et_code.text.toString())
            }
        }
    }


}