package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.util.Log
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.model.QiQiRuleModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRuleViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityQiqiRuleBinding

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiRuleActivity : BaseActivity<ActivityQiqiRuleBinding, QiQiRuleViewModel>() {


    override fun getBaseViewModel() = QiQiRuleViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_rule
    val TAG="QiQiRuleActivity"

    override fun init() {
        initTitle("七七规则")

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
        }
    }

    override fun loadData() {
        super.loadData()

        val json = "{\"cmd\":\"getRuleList"  +
                "\"}"
        viewModel!!.getRule(json).bindLifeCycle(this)
            .subscribe({

            }, { toastFailure(it) })
    }

}