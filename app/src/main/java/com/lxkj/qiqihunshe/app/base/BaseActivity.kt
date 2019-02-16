package com.lxkj.qiqihunshe.app.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Slingge on 2018/11/13
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>: AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract fun getBaseViewModel(): VM
    var viewModel: VM? = null


    abstract fun getLayoutId(): Int
    protected abstract fun init()
    protected open fun loadData() {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setLifecycleOwner(this)

        viewModel = getBaseViewModel()
        viewModel?.let {
            it.activity = this
        }
        init()
        loadData()
    }


    override fun onDestroy() {
        super.onDestroy()
        //解除ViewModel生命周期感应
        viewModel?.let {
            it.detachView()
            viewModel = null
        }
        binding.unbind()
    }

}