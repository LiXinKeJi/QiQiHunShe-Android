package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiBlackListViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/26
 */
class QiQiBlackListActivity : BaseActivity<ActivityRecyvlerviewBinding, QiQiBlackListViewModel>() {


    override fun getBaseViewModel() = QiQiBlackListViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        initTitle("七七黑名单")
        viewModel?.let {
            it.bind = binding
            it.initViewModel()
        }

        refresh.setOnRefreshListener {
            refresh.isRefreshing=false
        }

    }


}