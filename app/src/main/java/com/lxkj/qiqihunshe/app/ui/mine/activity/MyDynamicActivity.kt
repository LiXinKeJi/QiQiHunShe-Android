package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/25
 */
class MyDynamicActivity : BaseActivity<ActivityMydynamicBinding, MyDynamicViewModel>() {
    override fun getBaseViewModel() = MyDynamicViewModel()

    override fun getLayoutId()= R.layout.activity_mydynamic

    override fun init() {
       initTitle("我的动态")


        tv_right.visibility= View.VISIBLE
        tv_right.text = "删除"
        tv_right.setOnClickListener {

        }

        viewModel?.let {
            binding.viewmodel=it
            it.bind=binding
            it.initViewModel()
        }
    }

}