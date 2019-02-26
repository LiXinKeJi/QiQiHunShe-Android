package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyProblemViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * 我的问题
 * Created by Slingge on 2019/2/26
 */
class MyProblemFragment :BaseFragment<ActivityRecyvlerviewBinding,MyProblemViewModel>(){

    override fun getBaseViewModel()=MyProblemViewModel ()

    override fun getLayoutId()= R.layout.activity_recyvlerview
    override fun init() {
        include.visibility= View.GONE

        viewModel?.let {
            it.bind=binding
            it.initViewModel()
        }
    }

    override fun loadData() {
    }

}