package com.lxkj.qiqihunshe.app.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Slingge on 2017/4/21 0021.
 */

abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false


    protected lateinit var binding: VB
    protected abstract fun getBaseViewModel(): VM
    var viewModel: VM? = null

    abstract fun getLayoutId(): Int
    protected abstract fun init()

      var one=false//默认只加载一次，true每次进入页面都加载

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
        viewModel = getBaseViewModel()
        viewModel?.let {
            it.fragment = this
        }
        init()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    abstract fun loadData()

    @JvmOverloads
    fun prepareFetchData(forceUpdate: Boolean = refreshData): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData()
            isDataInitiated = true
            refreshData = one//false,只加载一次，true每次进入页面都加载
            return true
        }
        return false
    }

    companion object {

        var refreshData: Boolean = false
    }

}
