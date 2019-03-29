package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRecommendFragmentViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/22
 */
class QiQiRecommendFragment : BaseFragment<ActivityXrecyclerviewBinding, QiQiRecommendFragmentViewModel>() {

    private var flag = 1//1七七推荐，2定制推荐

    override fun getBaseViewModel() = QiQiRecommendFragmentViewModel()

    override fun getLayoutId() = R.layout.activity_xrecyclerview


    override fun init() {
        include.visibility = View.GONE
        flag = arguments!!.getInt("flag", -1)

        viewModel?.let {
            it.bind = binding
        }

    }

    override fun loadData() {
        viewModel!!.init(flag.toString())
    }


}