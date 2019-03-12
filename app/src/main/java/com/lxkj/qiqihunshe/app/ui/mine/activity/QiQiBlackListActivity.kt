package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiBlackListViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/26
 */
class QiQiBlackListActivity : BaseActivity<ActivityRecyvlerviewBinding, QiQiBlackListViewModel>() {


    override fun getBaseViewModel() = QiQiBlackListViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview
    var page = 0


    override fun init() {
        initTitle("七七黑名单")
        viewModel?.let {
            it.bind = binding
            it.initViewModel()

            it.adapter.setLoadMore {
                it.page++
                if (it.page <= it.totalpage) {
                    viewModel!!.getBlackData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }

        refresh.setOnRefreshListener {
            page = 1
            loadData()
        }

    }

    override fun loadData() {
        viewModel!!.getBlackData().bindLifeCycle(this)
            .subscribe({}, { toastFailure(it) })
    }


}