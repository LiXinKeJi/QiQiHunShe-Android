package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupEconomicsViewModel
import com.lxkj.qiqihunshe.databinding.FragmentLookupEconomicsBinding
import kotlinx.android.synthetic.main.fragment_lookup_accurate.*

/**
 * 条件查找
 * Created by Slingge on 2019/3/1
 */
class LookupEconomicsFragment : BaseFragment<FragmentLookupEconomicsBinding, LookupEconomicsViewModel>() {

    override fun getBaseViewModel() = LookupEconomicsViewModel()

    override fun getLayoutId() = R.layout.fragment_lookup_economics

    override fun init() {

        tv_search.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "经济查找")
            MyApplication.openActivity(activity, LookupResultActivity::class.java, bundle)
        }

    }

    override fun loadData() {
    }

}