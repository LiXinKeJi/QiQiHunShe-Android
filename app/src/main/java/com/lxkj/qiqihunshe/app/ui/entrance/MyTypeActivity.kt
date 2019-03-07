package com.lxkj.qiqihunshe.app.ui.entrance

import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.MyTypeViewModel
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityMytypeBinding
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/19
 */
class MyTypeActivity : BaseActivity<ActivityMytypeBinding, MyTypeViewModel>() {


    override fun getBaseViewModel() = MyTypeViewModel()

    override fun getLayoutId() = R.layout.activity_mytype

    override fun init() {
        initTitle("我的类型")
        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"

        tv_right.setOnClickListener {
            viewModel?.let {
                it.back()
            }
        }

        val flag = intent.getIntExtra("flag", 0)

        when (flag) {
            1 -> initTitle("我的类型")
            2 -> initTitle("兴趣爱好")
            3 -> initTitle("地点标签")
            4 -> initTitle("ta的类型")
        }

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
//            abLog.e("list", Gson().toJson(intent.getStringArrayListExtra("list")))
//            abLog.e("flag", Gson().toJson(intent.getIntExtra("flag",0)))
            it.lable.addAll(intent.getStringArrayListExtra("list"))
            it.flag = intent.getIntExtra("flag", 0)
            it.initTLable()
        }

    }


}