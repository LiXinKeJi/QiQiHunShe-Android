package com.lxkj.qiqihunshe.app.ui.mine.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.AboutMeFragmentViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*

/**
 * Created by kxn on 2019/3/6 0006.
 */
class AboutMeFragment : BaseFragment<ActivityXrecyclerviewBinding, AboutMeFragmentViewModel>() {

    private var flag = 1//1谁喜欢我，2谁看过我

    override fun getBaseViewModel() = AboutMeFragmentViewModel()

    override fun getLayoutId() = R.layout.activity_xrecyclerview


    override fun init() {
        include.visibility = View.GONE
        flag = arguments!!.getInt("flag", 1)

        viewModel?.let {
            it.bind=binding
            it.init(flag.toString())
        }

    }

    override fun loadData() {

    }


}