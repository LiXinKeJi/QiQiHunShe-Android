package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.QiQiRemindViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding

/**
 * Created by Slingge on 2019/3/1
 */
class QiQiRemindActivity : BaseActivity<ActivityRecyvlerviewBinding, QiQiRemindViewModel>() {

    override fun getBaseViewModel() = QiQiRemindViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview

    override fun init() {
        initTitle("小七提醒")
        viewModel?.let {
            it.bind=binding
            it.initViewmodel()
        }
    }


}