package com.lxkj.qiqihunshe.app.ui.entrance.fragment

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.WelComeFragmentViewModel
import com.lxkj.qiqihunshe.databinding.FragmentWelcomeBinding
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * Created by Slingge on 2019/4/4
 */
class WelComeFragment : BaseFragment<FragmentWelcomeBinding, WelComeFragmentViewModel>() {

    override fun getBaseViewModel() = WelComeFragmentViewModel()

    override fun getLayoutId() = R.layout.fragment_welcome

    interface WelComeNextCallBack {
        fun welCome(flag: Int)
    }

    private lateinit var welComeNextCallBack: WelComeNextCallBack

    fun SWelComeNextCallBack(welComeNextCallBack: WelComeNextCallBack) {
        this.welComeNextCallBack = welComeNextCallBack
    }

    override fun init() {
        viewModel?.let {
            it.bind = binding
            it.flag = arguments!!.getInt("flag", -1)
            it.init()
        }

        image.setOnClickListener {
            welComeNextCallBack.welCome(viewModel!!.flag)
        }
    }

    override fun loadData() {
    }
}