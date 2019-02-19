package com.lxkj.qiqihunshe.app.ui.entrance

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.MyTypeViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMytypeBinding
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/19
 */
class MyTypeActivity : BaseActivity<ActivityMytypeBinding, MyTypeViewModel>() {


    override fun getBaseViewModel() = MyTypeViewModel()

    override fun getLayoutId() = R.layout.activity_mytype

    override fun init() {
        WhiteStatusBar()
        initTitle("我的类型")
        tv_right.visibility= View.VISIBLE
        tv_right.text="保存"

        viewModel?.let {
            binding.viewmodel=it
            it.bind=binding

            it.initTLable()
        }

    }


}