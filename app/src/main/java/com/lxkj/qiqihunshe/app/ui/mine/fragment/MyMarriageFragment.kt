package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyMarriageViewModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyProblemViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * 我的情感-我的征婚(与我的空间-我的邀约item相同)
 * Created by Slingge on 2019/2/26
 */
class MyMarriageFragment :BaseFragment<ActivityRecyvlerviewBinding,MyMarriageViewModel>(){

    override fun getBaseViewModel()=MyMarriageViewModel ()

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