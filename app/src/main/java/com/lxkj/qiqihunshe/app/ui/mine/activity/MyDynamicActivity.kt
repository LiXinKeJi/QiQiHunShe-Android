package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import kotlinx.android.synthetic.main.activity_mydynamic.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * 我的动态、动态详情
 * Created by Slingge on 2019/2/25
 */
class MyDynamicActivity : BaseActivity<ActivityMydynamicBinding, MyDynamicViewModel>() {

    private var flag = -1//0我的动态，1动态详情

    override fun getBaseViewModel() = MyDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_mydynamic

    override fun init() {
        initTitle("我的动态")

        flag = intent.getIntExtra("flag", -1)

        if(flag==0){
            cl_person.visibility=View.GONE
            tv_reward.visibility=View.GONE
        }

        tv_right.visibility = View.VISIBLE
        tv_right.text = "删除"
        tv_right.setOnClickListener {

        }

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.initViewModel()
        }


    }

}