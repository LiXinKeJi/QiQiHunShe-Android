package com.lxkj.qiqihunshe.app.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import cn.jzvd.Jzvd
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.databinding.ActivityMainBinding
import com.lxkj.qiqihunshe.app.AppConsts
import com.lxkj.qiqihunshe.app.service.LocationService
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.util.*


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getBaseViewModel() = MainViewModel()

    override fun getLayoutId() = R.layout.activity_main


    override fun init() {

        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }
        viewModel?.let {
            it.bind = binding
            it.framanage = supportFragmentManager
            it.initBind()
        }

        if (PermissionUtil.LocationPermissionAlbum(this, 0)) {
            initLocationOption()
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            initLocationOption()
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要定位权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel!!.fuJinFragment.onActivityResult(requestCode, resultCode, data)
        viewModel!!.mineFragment.onActivityResult(requestCode, resultCode, data)
    }


    private fun initLocationOption() {
        val intentOne = Intent(this, LocationService::class.java)
        startService(intentOne)
    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }


}
