package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QuestionsAuthenViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityQuestionsAuthenBinding
import kotlinx.android.synthetic.main.activity_questions_authen.*
import kotlinx.android.synthetic.main.dialog_report1.*

/**
 * Created by Slingge on 2019/3/2
 */
class QuestionsAuthenActivity :
    BaseActivity<ActivityQuestionsAuthenBinding, QuestionsAuthenViewModel>() {


    override fun getBaseViewModel() = QuestionsAuthenViewModel()

    override fun getLayoutId() = R.layout.activity_questions_authen

    override fun init() {
        initTitle("问答认证")

        viewModel?.let {
            binding.viewmodel = it
            it.getQuestions()
        }


        tv_next.setOnClickListener {
            if (!check.isChecked) {
                ToastUtil.showTopSnackBar(this, "请选择无争议")
                return@setOnClickListener
            }
            viewModel?.next()
            check.isChecked = false
        }
    }


}