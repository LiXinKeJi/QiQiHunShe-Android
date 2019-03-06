package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.model.WithdrawalModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.WithdrawalViewModel
import com.lxkj.qiqihunshe.app.util.CashierInputFilter
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityWithdrawalBinding
import kotlinx.android.synthetic.main.activity_withdrawal.*

/**
 * Created by Slingge on 2019/2/22
 */
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding, WithdrawalViewModel>(), View.OnClickListener {


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
            tv_totalmoney.text = "可提现金额￥ ${StaticUtil.amount}"
            it.getProportion().bindLifeCycle(this).subscribe({}, { it })
        }

        tv_all.setOnClickListener(this)
        rl_type.setOnClickListener(this)
        tv_enter.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_all -> et_money.setText(StaticUtil.amount)

            R.id.rl_type -> {
//                viewModel!!.selectState()
            }
            R.id.tv_enter -> {
                model.nitifyMoney()
                if (TextUtils.isEmpty(model.amount)) {
                    ToastUtil.showTopSnackBar(this, "请输入提现金额")
                    return
                }
                if (model.amount.toDouble() > StaticUtil.amount.toDouble()) {
                    ToastUtil.showTopSnackBar(this, "余额不足")
                    return
                }

                if (TextUtils.isEmpty(model.account)) {
                    ToastUtil.showTopSnackBar(this, "请输入提现账户")
                    return
                }
                if (TextUtils.isEmpty(model.realname)) {
                    ToastUtil.showTopSnackBar(this, "请输入提现账户姓名")
                    return
                }

                viewModel!!.Withdrawal(model).bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }
}