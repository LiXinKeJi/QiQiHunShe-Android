package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.InputFilter
import android.text.TextUtils
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ChargingSetUpViewModel
import com.lxkj.qiqihunshe.app.util.CashierInputFilter
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityChargingSetupBinding
import kotlinx.android.synthetic.main.activity_charging_setup.*

/**
 * Created by Slingge on 2019/2/25
 */
class ChargingSetUpActivity :
    BaseActivity<ActivityChargingSetupBinding, ChargingSetUpViewModel>() {
    override fun getBaseViewModel() = ChargingSetUpViewModel()

    override fun getLayoutId() = R.layout.activity_charging_setup

    override fun init() {
        initTitle("收费设置")

        val filter = arrayOf<InputFilter>(CashierInputFilter())
        et_phone.filters = filter
        et_code.filters = filter

        viewModel?.let {
            binding.viewmodel = it
            binding.model = it.model
            it.bind=binding

            it.getcaiyiFee().bindLifeCycle(this).subscribe({},{toastFailure(it)})
        }


        tv_enter.setOnClickListener {
            viewModel?.let {
                it.model.noti()
                if (TextUtils.isEmpty(it.model.voice)) {
                    ToastUtil.showTopSnackBar(this, "请输入语音费用")
                    return@setOnClickListener
                }
                if (TextUtils.isEmpty(it.model.video)) {
                    ToastUtil.showTopSnackBar(this, "请输入视频费用")
                    return@setOnClickListener
                }

                it.caiyiFee().bindLifeCycle(this).subscribe({},{toastFailure(it)})
            }
        }

    }


}