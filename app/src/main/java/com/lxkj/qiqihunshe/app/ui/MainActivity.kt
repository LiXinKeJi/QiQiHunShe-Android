package com.lxkj.qiqihunshe.app.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import cn.jzvd.Jzvd
import com.baidu.location.*
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.databinding.ActivityMainBinding
import com.lxkj.qiqihunshe.app.ui.quyu.QuYuFragment.MyLocationListener
import com.lxkj.qiqihunshe.app.AppConsts
import com.lxkj.qiqihunshe.app.service.LocationService
import com.lxkj.qiqihunshe.app.util.*


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    val mLocationClient by lazy { LocationClient(this) }
    override fun getBaseViewModel() = MainViewModel()

    override fun getLayoutId() = R.layout.activity_main


    override fun init() {
        StaticUtil.lat = SharePrefUtil.getString(this, AppConsts.LAT, "")
        StaticUtil.lng = SharePrefUtil.getString(this, AppConsts.LNG, "")

        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }
        viewModel?.let {
            it.bind = binding
            it.framanage = supportFragmentManager
            it.initBind()
        }

    }




    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }


}
