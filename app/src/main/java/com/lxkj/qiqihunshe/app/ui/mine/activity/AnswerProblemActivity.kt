package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.text.TextUtils
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AnswerProblemViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityAnswerProblemBinding
import kotlinx.android.synthetic.main.activity_answer_problem.*

/**
 * Created by Slingge on 2019/2/20
 */
class AnswerProblemActivity : BaseActivity<ActivityAnswerProblemBinding, AnswerProblemViewModel>() {


    override fun getBaseViewModel() = AnswerProblemViewModel()

    override fun getLayoutId() = R.layout.activity_answer_problem

    override fun init() {
        initTitle("回答验证")

        viewModel?.let {
            binding.viewmodel = it
            it.getproblem().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }


        tv_enter.setOnClickListener {
            viewModel?.let {
                if (TextUtils.isEmpty(it.answer.get())) {
                    ToastUtil.showTopSnackBar(this, "请输入您的答案")
                    return@setOnClickListener
                }
                val bundle = Bundle()
                bundle.putString("answer", it.answer.get())
                MyApplication.openActivity(this, ModifyPhoneActivity::class.java, bundle)
            }

        }

    }


}