package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.net.Uri
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiDynamicDetailsViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
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
        tv_right.visibility = View.VISIBLE
        tv_right.text = "取消报名"
        tv_right.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind=binding
            it.id = intent.getStringExtra("id")
            it.getDate().bindLifeCycle(this).subscribe({
                if (viewModel!!.model.status == "0" || viewModel!!.model.status == "3") {
                    tv_right.visibility = View.GONE
                }
            }, { toastFailure(it) })
        }

        tv_call.setOnClickListener(this)
        tv_sginup.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_call -> {
                viewModel!!.toCallPhone()
            }
            R.id.tv_right -> {
                viewModel!!.signUp("1").bindLifeCycle(this).subscribe({},{toastFailure(it)})
            }
            R.id.tv_sginup -> {
                viewModel!!.signUp("0").bindLifeCycle(this).subscribe({},{toastFailure(it)})
            }
        }
    }


}