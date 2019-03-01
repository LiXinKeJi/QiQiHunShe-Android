package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupAccurateViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupAccurateBinding
import kotlinx.android.synthetic.main.fragment_lookup_accurate.*

/**
 * Created by Slingge on 2019/3/1
 */
class LookupAccurateFragment :
    BaseFragment<FragmentLookupAccurateBinding, LookupAccurateViewModel>() {


    override fun getBaseViewModel() = LookupAccurateViewModel()

    override fun getLayoutId() = R.layout.fragment_lookup_accurate

    override fun init() {
        viewModel?.let {
            binding.viewmodel = it
        }

        tv_search.setOnClickListener {
            ToastUtil.showToast(viewModel!!.phone.get().toString())
            val bundle = Bundle()
            bundle.putString("title", "精确查找")
            MyApplication.openActivity(activity, LookupResultActivity::class.java, bundle)
        }

    }

    override fun loadData() {

    }

}