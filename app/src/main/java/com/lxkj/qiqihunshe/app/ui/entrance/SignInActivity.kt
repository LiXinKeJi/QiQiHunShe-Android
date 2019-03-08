package com.lxkj.qiqihunshe.app.ui.entrance

import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.View
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.service.LocationService
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.SignInViewModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivitySigninBinding
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/16
 */
class SignInActivity : BaseActivity<ActivitySigninBinding, SignInViewModel>(), View.OnClickListener {
    val mLocationClient by lazy { LocationClient(this) }
    override fun getBaseViewModel() = SignInViewModel()

    override fun getLayoutId() = R.layout.activity_signin

    private var sginModel = SignInModel()

    override fun init() {
        initTitle("关闭")
        AbStrUtil.setDrawableLeft(this, -1, tv_title, 0)

        tv_forgetpass.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_sginin.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            binding.model = sginModel
            it.bind = binding
            it.headerUrl.set(StaticUtil.headerUrl)
        }

        if (PermissionUtil.LocationPermissionAlbum(this, 0)) {
            initLocationOption()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_forgetpass -> {
                MyApplication.openActivity(this, ForgetPassActivity::class.java)
            }
            R.id.tv_register -> {
                MyApplication.openActivity(this, RegisterActivity::class.java)
            }
            R.id.tv_sginin -> {

                sginModel.notif()
                if (TextUtils.isEmpty(sginModel.phone)) {
                    ToastUtil.showToast("请输入手机号/七七账号")
                    return
                }
                if (TextUtils.isEmpty(sginModel.pass)) {
                    ToastUtil.showToast("请输入密码")
                    return
                }

                val json =
                    "{\"cmd\":\"userLogin\",\"phone\":\"" + sginModel.phone + "\",\"password\":\"" +
                            Md5Util.md5Encode(sginModel.pass) + "\",\"token\":\"" + "" + "\"}"
                viewModel!!.sginIn(json).bindLifeCycle(this)
                    .subscribe({}, { toastFailure(it) })
            }
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


    private fun initLocationOption() {
        val intentOne = Intent(this, LocationService::class.java)
        startService(intentOne)
    }


}