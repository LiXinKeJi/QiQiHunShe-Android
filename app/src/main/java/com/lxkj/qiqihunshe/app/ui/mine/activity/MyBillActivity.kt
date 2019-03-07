package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyBillViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMybillBinding
import kotlinx.android.synthetic.main.activity_mybill.*

/**
 * Created by Slingge on 2019/2/22
 */
class MyBillActivity : BaseActivity<ActivityMybillBinding, MyBillViewModel>() {


    override fun getBaseViewModel() = MyBillViewModel()

    override fun getLayoutId() = R.layout.activity_mybill

    override fun init() {
        WhiteStatusBar()
        initTitle("我的账单")

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
//            it.getBill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }


        tv_start.setOnClickListener {
            viewModel?.showDateWheel(0)
        }
        tv_end.setOnClickListener {
            viewModel?.showDateWheel(1)
        }
        tv_enter.setOnClickListener {
            viewModel?.page = 1
            viewModel?.getBill()!!.bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }


}