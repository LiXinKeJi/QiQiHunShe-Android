package com.lxkj.qiqihunshe.app.ui.shouye.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.MatchingHistoryViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMatchHistoryBinding

/**
 * Created by Slingge on 2019/2/26
 */
class MatchingHistoryActivity : BaseActivity<ActivityMatchHistoryBinding, MatchingHistoryViewModel>() {


    override fun getBaseViewModel() = MatchingHistoryViewModel()

    override fun getLayoutId() = R.layout.activity_match_history

    override fun init() {
        initTitle("历史匹配")

        viewModel?.let {
            it.bind = binding
            it.flag = intent.getIntExtra("flag", -1)
            it.initViewModel()
        }
    }
}