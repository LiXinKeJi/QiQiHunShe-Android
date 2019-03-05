package com.lxkj.qiqihunshe.app.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.util.AppManager
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.StatusBarBlackWordUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2018/11/13
 */
abstract class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var binding: VB
    protected abstract fun getBaseViewModel(): VM
    var viewModel: VM? = null


    abstract fun getLayoutId(): Int
    protected abstract fun init()
    protected open fun loadData() {}

    var isWhiteStatusBar = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setLifecycleOwner(this)

        viewModel = getBaseViewModel()
        viewModel?.let {
            it.activity = this
        }
        init()
        WhiteStatusBar()
        loadData()

        AppManager.addActivity(this)
    }


    fun initTitle(title: String) {
        tv_title.text = title
        tv_title.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //白色状态栏
    fun WhiteStatusBar() {
        if (isWhiteStatusBar && Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.white))
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }
    }


    //网络异常捕捉
    fun toastFailure(error: Throwable?) {
        ProgressDialogUtil.dismissDialog()
        dispatchFailure(this, error)
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