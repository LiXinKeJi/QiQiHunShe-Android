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

    override fun init() {

        viewModel?.let {
            it.bind = binding
            it.flag=intent.getIntExtra("flag",-1)
            if(it.flag==0){
                initTitle("七七黑名单")
            }else{
                initTitle("我的黑名单")
            }
            it.initViewModel()

            it.adapter.setLoadMore {
                it.page++
                if (it.page <= it.totalpage) {
                    data()
                }
            }
        }

        refresh.setOnRefreshListener {
            viewModel?.page = 1
            data()
        }
        data()
    }

      fun data() {
        viewModel!!.getBlackData().bindLifeCycle(this)
            .subscribe({}, { toastFailure(it) })
    }


}