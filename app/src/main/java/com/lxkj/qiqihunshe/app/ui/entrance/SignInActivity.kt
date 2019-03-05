package com.lxkj.qiqihunshe.app.ui.entrance

import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.SignInViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.Md5Util
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivitySigninBinding
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/16
 */
class SignInActivity : BaseActivity<ActivitySigninBinding, SignInViewModel>(), View.OnClickListener {

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
                    "{\"cmd\":\"userLogin\",\"phone\":\"" + sginModel.phone + "\",\"password\":\"" + Md5Util.md5Encode(
                        sginModel.pass
                    ) +
                            "\",\"token\":\"" + "" + "\"}"
                viewModel!!.sginIn(json).bindLifeCycle(this)
                    .subscribe({}, { toastFailure(it) })
            }
        }
    }

}