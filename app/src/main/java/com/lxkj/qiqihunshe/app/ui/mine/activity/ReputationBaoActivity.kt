package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReputationBaoViewModel
import com.lxkj.qiqihunshe.databinding.ActivityReputationBaoBinding
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

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right -> {
                MyApplication.openActivity(this, QiQiRuleActivity::class.java)
            }
        }
    }


}