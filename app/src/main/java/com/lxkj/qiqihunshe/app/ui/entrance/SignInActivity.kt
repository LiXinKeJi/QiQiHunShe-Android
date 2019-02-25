package com.lxkj.qiqihunshe.app.ui.entrance

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.SignInViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
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
            it.headerUrl.set("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550480316103&di=ce1db19c0c33c67a3cc17764e598fc94&imgtype=0&src=http%3A%2F%2Fpic21.nipic.com%2F20120519%2F5454342_154115399000_2.jpg")
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
              MyApplication.openActivity(this,MainActivity::class.java)
            }
        }
    }

}