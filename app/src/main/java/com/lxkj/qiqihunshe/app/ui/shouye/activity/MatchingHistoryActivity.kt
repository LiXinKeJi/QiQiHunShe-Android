package com.lxkj.qiqihunshe.app.ui.shouye.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.MatchingHistoryViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMatchHistoryBinding
import kotlinx.android.synthetic.main.activity_withdrawal.*

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
            var flag = intent.getIntExtra("flag", -1)
            when (flag) {
                0 -> {
                    it.type = 1
                }
                1 -> {
                    it.type = 2
                }
                2 -> {//匹配
                    initTitle("匹配结果")
                }
            }
            it.init()

        }
    }
}