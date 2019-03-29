package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.activity.InteractiveNotificationActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.QiQiRemindActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.XiangShiViewModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import com.lxkj.qiqihunshe.databinding.FraXiangshiBinding
import kotlinx.android.synthetic.main.fra_xiangshi.*

/**
 * Created by Slingge on 2019/2/28
 */
class XiangShiFragment : BaseFragment<FraXiangshiBinding, XiangShiViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = XiangShiViewModel()

    override fun getLayoutId() = R.layout.fra_xiangshi

    override fun init() {
        include.visibility = View.GONE
        viewModel?.let {
            it.bind = binding
            viewModel?.getNewMsg()
        }

        llHint.setOnClickListener(this)
        llNotify.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        viewModel?.getNewMsg()
    }

    override fun loadData() {
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.llHint -> { //小七提醒
                MyApplication.openActivity(context, QiQiRemindActivity::class.java)
            }
            R.id.llNotify -> { //互动通知
                MyApplication.openActivity(context, InteractiveNotificationActivity::class.java)
            }
        }
    }


}