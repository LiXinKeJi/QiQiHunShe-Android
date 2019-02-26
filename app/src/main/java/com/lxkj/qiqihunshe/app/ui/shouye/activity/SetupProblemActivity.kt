package com.lxkj.qiqihunshe.app.ui.shouye.activity

import android.os.Bundle
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.SetupProblemViewModel
import com.lxkj.qiqihunshe.databinding.ActivitySetupProblemBinding
import kotlinx.android.synthetic.main.activity_setup_problem.*

/**
 * Created by Slingge on 2019/2/26
 */
class SetupProblemActivity : BaseActivity<ActivitySetupProblemBinding, SetupProblemViewModel>() {


    override fun getBaseViewModel() = SetupProblemViewModel()

    override fun getLayoutId() = R.layout.activity_setup_problem


    override fun init() {
        initTitle("设置问题")

        tv_next.setOnClickListener {
            val bundle=Bundle()
            bundle.putInt("flag",2)
            MyApplication.openActivity(this,MatchingHistoryActivity::class.java,bundle)
        }
    }
}