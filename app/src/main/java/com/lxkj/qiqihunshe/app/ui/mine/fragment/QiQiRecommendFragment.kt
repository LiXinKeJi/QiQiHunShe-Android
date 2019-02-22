package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiRecommendFragmentViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by Slingge on 2019/2/22
 */
class QiQiRecommendFragment : BaseFragment<ActivityRecyvlerviewBinding, QiQiRecommendFragmentViewModel>() {

    private var flag = -1//0七七推荐，1定制推荐

    override fun getBaseViewModel() = QiQiRecommendFragmentViewModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview


    override fun init() {
        include.visibility = View.GONE
        flag = arguments!!.getInt("flag", -1)


        refresh.setOnRefreshListener {
            //刷新监听

        }

    }

    override fun loadData() {
        ToastUtil.showToast(flag.toString())
        viewModel?.let {
            it.bind=binding
            it.initViewModel()
        }
    }


}