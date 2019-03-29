package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.exception.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.PayViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityPaymentBinding
import kotlinx.android.synthetic.main.activity_payment.*

/**
 * Created by Slingge on 2019/3/12
 */
class PayActivity : BaseActivity<ActivityPaymentBinding, PayViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = PayViewModel()

    override fun getLayoutId() = R.layout.activity_payment

    private var flag = -1//0包括余额支付，1不能余额支付

    override fun init() {
        initTitle("选择支付方式")

        viewModel?.let {
            binding.viewmodel = it
            it.initViewModel()
            it.payMoney = intent.getDoubleExtra("money", 0.0)
            it.bannale.set(it.payMoney.toString())
            it.num = intent.getStringExtra("num") + it.randomNum()//拼接4位随机数
            flag = intent.getIntExtra("flag", -1)
            if (flag != 0) {
                tv_balance.visibility = View.GONE
                cb_balance.visibility = View.GONE
                tv_weixin.setPadding(0, 35, 0, 0)
            }
        }

        cb_balance.setOnClickListener(this)
        cb_weixin.setOnClickListener(this)
        cb_zhifubao.setOnClickListener(this)
        tv_pay.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cb_balance -> {
                cb_weixin.isChecked = false
                cb_zhifubao.isChecked = false
                viewModel!!.type = 0
            }
            R.id.cb_weixin -> {
                cb_balance.isChecked = false
                cb_zhifubao.isChecked = false
                viewModel!!.type = 1
            }
            R.id.cb_zhifubao -> {
                cb_balance.isChecked = false
                cb_weixin.isChecked = false
                viewModel!!.type = 2
            }
            R.id.tv_pay -> {
                viewModel?.let {
                    if (it.type == -1) {
                        ToastUtil.showTopSnackBar(this, "请选择支付方式")
                        return
                    }
                    if (it.type == 0) {//余额支付
                        if (StaticUtil.amount.toDouble() < it.payMoney) {
                            ToastUtil.showTopSnackBar(this, "余额不足")
                            return
                        }
                        it.balanne().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                    } else {
                        it.Pay()
                    }
                }
            }
        }
    }


}