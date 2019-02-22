package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.CommentRecordViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * 点评记录
 * Created by Slingge on 2019/2/22
 */
class CommentRecordFragment : BaseFragment<ActivityRecyvlerviewBinding, CommentRecordViewModel>() {



    override fun getBaseViewModel() = CommentRecordViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview


    override fun init() {
        include.visibility = View.GONE


        refresh.setOnRefreshListener {
            //刷新监听

        }

    }

    override fun loadData() {

        viewModel?.let {
            it.bind=binding
            it.initViewModel()
        }
    }


}