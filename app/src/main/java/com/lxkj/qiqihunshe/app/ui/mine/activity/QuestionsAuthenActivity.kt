package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QuestionsAuthenViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQuestionsAuthenBinding

/**
 * Created by Slingge on 2019/3/2
 */
class QuestionsAuthenActivity :
    BaseActivity<ActivityQuestionsAuthenBinding, QuestionsAuthenViewModel>() {


    override fun getBaseViewModel() = QuestionsAuthenViewModel()

    override fun getLayoutId() = R.layout.activity_questions_authen

    override fun init() {
        initTitle("问答认证")

    }


}