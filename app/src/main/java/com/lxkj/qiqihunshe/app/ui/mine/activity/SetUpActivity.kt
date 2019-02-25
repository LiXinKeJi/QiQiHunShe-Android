package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.SetUpViewModel
import com.lxkj.qiqihunshe.app.util.DataCleanManager
import com.lxkj.qiqihunshe.app.util.ThreadUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivitySetupBinding
import kotlinx.android.synthetic.main.activity_setup.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/19
 */
class SetUpActivity : BaseActivity<ActivitySetupBinding, SetUpViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = SetUpViewModel()

    override fun getLayoutId() = R.layout.activity_setup

    override fun init() {
        EventBus.getDefault().register(this)
        initTitle("设置")

        viewModel?.let {
            binding.viewmodel = it
        }

        fl_updata.setOnClickListener(this)
        tv_setup.setOnClickListener(this)
        tv_feedback.setOnClickListener(this)
        tv_contact.setOnClickListener(this)
        tv_agreement.setOnClickListener(this)
        rl_clear.setOnClickListener(this)
        tv_singout.setOnClickListener(this)

        tv_cache.text = DataCleanManager.getTotalCacheSize(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_clear -> {
                DataCleanManager.clearAllCache(this)
                tv_cache.text = "0.0MB"
            }
            R.id.tv_agreement -> {
                MyApplication.openActivity(this, AgreementActivity::class.java)
            }
            R.id.tv_contact -> {
                MyApplication.openActivity(this, ContactUsActivity::class.java)
            }
            R.id.tv_feedback -> {
                MyApplication.openActivity(this, FeedBackActivity::class.java)
            }
            R.id.tv_setup -> {
                MyApplication.openActivity(this, SecuritySetUpActivity::class.java)
            }
            R.id.tv_singout -> {
                viewModel?.sginout()
            }
            R.id.fl_updata -> {
                viewModel?.upData()
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            viewModel?.upData()
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要读写内存文件权限")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                viewModel?.REQUEST_INSTALL -> {
                    viewModel?.initApk()
                }
            }
        }
    }


    @Subscribe
    fun onEvent(msg: String) {
        if (TextUtils.isEmpty(msg)) {
            ThreadUtil.runOnMainThread(Runnable {
                ToastUtil.showTopSnackBar(this, "网络错误")
            })
            viewModel?.isDownApk = false
        } else {
            viewModel?.initApk()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}