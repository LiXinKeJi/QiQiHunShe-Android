package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.DynamicSignUpAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.DynamicSignUpDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiDynamicDetailsViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicDetailsBinding
import kotlinx.android.synthetic.main.activity_qiqi_dynamic_details.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicDetailsActivity : BaseActivity<ActivityQiqiDynamicDetailsBinding, QiQiDynamicDetailsViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = QiQiDynamicDetailsViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_dynamic_details

    override fun init() {
        initTitle("活动详情")

        tv_sginup.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it

        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_sginup -> {//报名
                DynamicSignUpDialog.sginUpShow(this,"活动标题")
            }
        }
    }


    //已报名
    fun isSginUp() {
        tv_right.visibility = View.VISIBLE//报名之后
        tv_right.text = "取消报名"

        rl_call.visibility = View.VISIBLE
        tv_sginup.visibility = View.GONE
    }


    override fun onDestroy() {
        super.onDestroy()
        DynamicSignUpDialog.diss()
        DynamicSignUpAfterDialog.diss()
    }


}