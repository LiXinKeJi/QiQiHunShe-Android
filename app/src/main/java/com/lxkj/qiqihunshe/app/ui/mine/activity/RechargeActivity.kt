package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.InputFilter
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.RechargeModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.RechargeViewModel
import com.lxkj.qiqihunshe.app.util.CashierInputFilter
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRechargeBinding
import kotlinx.android.synthetic.main.activity_recharge.*

/**
 * Created by Slingge on 2019/2/22
 */
class RechargeActivity : BaseActivity<ActivityRechargeBinding, RechargeViewModel>() {


    override fun getBaseViewModel() = RechargeViewModel()

    override fun getLayoutId() = R.layout.activity_recharge

    private val  model by lazy { RechargeModel() }

    override fun init() {

        WhiteStatusBar()
        initTitle("充值")

        val filter = arrayOf<InputFilter>(CashierInputFilter())
        et_money.filters = filter

        viewModel?.let {
            binding.viewmodel=it
            binding.model=model

        }

        tv_enter.setOnClickListener {
            model.notifyMoney()
            ToastUtil.showToast(model.money)
        }
    }


}