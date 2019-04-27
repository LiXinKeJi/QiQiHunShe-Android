package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Build
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.WalletViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.databinding.ActivityWalletBinding
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/22
 */
class WalletActivity : BaseActivity<ActivityWalletBinding, WalletViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = WalletViewModel()

    override fun getLayoutId() = R.layout.activity_wallet


    override fun init() {
        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.immersiveStatusBar(this, 0f)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(this, view_staus)
        }

        rl_include.setBackgroundColor(resources.getColor(R.color.transparent))
        tv_title.setBackgroundColor(resources.getColor(R.color.transparent))
        tv_title.setTextColor(resources.getColor(R.color.white))
        AbStrUtil.setDrawableLeft(this, R.drawable.ic_back_w, tv_title, 10)

        initTitle("我的钱包")

        viewModel?.let {
            it.bind = binding
        }

        tv_recharge.setOnClickListener(this)
        tv_withdrawal.setOnClickListener(this)
        tv_bill.setOnClickListener(this)
        tv_buy.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        viewModel?.let {
            it.getBannel().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            it.getPermission().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_recharge -> {
                MyApplication.openActivity(this, RechargeActivity::class.java)
            }
            R.id.tv_withdrawal -> {
                MyApplication.openActivity(this, WithdrawalActivity::class.java)
            }
            R.id.tv_bill -> {
                MyApplication.openActivity(this, MyBillActivity::class.java)
            }
            R.id.tv_buy -> {
                MyApplication.openActivity(this, PermissionBuyActivity::class.java)
            }
        }
    }

}