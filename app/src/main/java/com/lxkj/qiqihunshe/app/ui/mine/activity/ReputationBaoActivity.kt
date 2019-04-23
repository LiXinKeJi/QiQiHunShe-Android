package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.dialog.BailRefundAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.BailRefundDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReputationBaoViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityReputationBaoBinding
import kotlinx.android.synthetic.main.activity_reputation_bao.*
import kotlinx.android.synthetic.main.dialog_report1.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/21
 */
class ReputationBaoActivity : BaseActivity<ActivityReputationBaoBinding, ReputationBaoViewModel>(),
    View.OnClickListener {

    override fun getBaseViewModel() = ReputationBaoViewModel()

    override fun getLayoutId() = R.layout.activity_reputation_bao

    override fun init() {
        initTitle("信誉宝")
        tv_right.visibility = View.VISIBLE
        tv_right.text = "七七规则"
        tv_right.setOnClickListener(this)
        tv_pay.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.userId = intent.getStringExtra("userId")
            if (it.userId != StaticUtil.uid) {
                rl_pay.visibility = View.GONE
            }
            it.initViewModel()
            getData()

            it.adapter.setLoadMore {
                it.page++
                if (it.page <= it.totalPage) {
                    it.getCreditList().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right -> {
                MyApplication.openActivity(this, QiQiRuleActivity::class.java)
            }
            R.id.tv_pay -> {//缴纳信誉金
                viewModel?.let {
                    if (it.bail == "0") {//信誉金 0代表未缴纳
                        viewModel!!.getReputationNum().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                    } else {//退还信誉金
                        if (it.reputModel.refundStatus == "1") {//// 信誉金退还状态 0没有退还申请(用户可申请）1有审核中的退还申请(不可申请)
                            ToastUtil.showTopSnackBar(this, "已申请退还信誉金，正在审核")
                            return
                        }
                        BailRefundDialog.show(this, object : BailRefundDialog.BailRefundCallBack {
                            override fun refund() {
                                it.BailRefund().bindLifeCycle(this@ReputationBaoActivity)
                                    .subscribe({}, { toastFailure(it) })
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == 303) {
            ToastUtil.showTopSnackBar(this, "缴纳信誉金成功")
            getData()
        }
    }


    fun getData(){
        viewModel?.let {
            it.getUserCredit().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            it.getCreditList().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            it.getReputationMoney().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BailRefundDialog.diss()
        BailRefundAfterDialog.diss()
    }

}