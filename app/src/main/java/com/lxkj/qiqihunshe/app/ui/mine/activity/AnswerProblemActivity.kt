package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AnswerProblemViewModel
import com.lxkj.qiqihunshe.databinding.ActivityAnswerProblemBinding
import kotlinx.android.synthetic.main.activity_answer_problem.*

/**
 * Created by Slingge on 2019/2/20
 */
class AnswerProblemActivity : BaseActivity<ActivityAnswerProblemBinding, AnswerProblemViewModel>() {


    override fun getBaseViewModel() = AnswerProblemViewModel()

    override fun getLayoutId() = R.layout.activity_answer_problem

    override fun init() {
        WhiteStatusBar()
        initTitle("回答验证")

        viewModel?.let {
            binding.viewmodel = it
        }


        tv_enter.setOnClickListener {
            MyApplication.openActivity(this, ModifyPhoneActivity::class.java)
        }

    }


}