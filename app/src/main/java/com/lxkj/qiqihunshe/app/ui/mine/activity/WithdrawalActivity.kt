package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.InputFilter
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.WithdrawalModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.WithdrawalViewModel
import com.lxkj.qiqihunshe.app.util.CashierInputFilter
import com.lxkj.qiqihunshe.databinding.ActivityWithdrawalBinding
import kotlinx.android.synthetic.main.activity_recharge.*

/**
 * Created by Slingge on 2019/2/22
 */
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding, WithdrawalViewModel>() {

    override fun getBaseViewModel() = WithdrawalViewModel()

    override fun getLayoutId() = R.layout.activity_withdrawal

    private val model by lazy { WithdrawalModel() }

    override fun init() {
        WhiteStatusBar()
        initTitle("提现")

        val filter = arrayOf<InputFilter>(CashierInputFilter())
        et_money.filters = filter

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.totalMoney.set("0.00")
        }
    }


}