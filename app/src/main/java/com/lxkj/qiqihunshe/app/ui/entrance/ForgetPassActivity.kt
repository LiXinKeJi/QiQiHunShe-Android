package com.lxkj.qiqihunshe.app.ui.entrance

import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
import com.lxkj.qiqihunshe.app.ui.entrance.model.ForgetPassModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.ForgetPassViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityForgetPassBinding
import kotlinx.android.synthetic.main.activity_forget_pass.*

/**
 * Created by Slingge on 2019/2/18
 */
class ForgetPassActivity : BaseActivity<ActivityForgetPassBinding, ForgetPassViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() =
        ForgetPassViewModel(RetrofitUtil.getRetrofitApi().create(RetrofitService::class.java))

    override fun getLayoutId() = R.layout.activity_forget_pass

    private val model by lazy { ForgetPassModel() }

    override fun init() {

        initTitle("设置新密码")

        tv_getCode.setOnClickListener(this)
        tv_enter.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model
            it.bind = binding
        }

        binding.tvGetCode
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_getCode -> {
                viewModel?.let {
                    model.notif()
                    if (TextUtils.isEmpty(model.phone)) {
                        ToastUtil.showTopSnackBar(this, "请输入手机号")
                        return
                    }
                    it.getCode()
                    it.getcode(model.phone)
                }
            }
            R.id.tv_enter -> {
                model.notif()

                if (TextUtils.isEmpty(model.phone)) {
                    ToastUtil.showTopSnackBar(this, "请输入手机号")
                    return
                }

                if (TextUtils.isEmpty(model.code)) {
                    ToastUtil.showTopSnackBar(this, "请输入验证码")
                    return
                }

                if (TextUtils.isEmpty(model.pass)) {
                    ToastUtil.showTopSnackBar(this, "请输入密码")
                    return
                }


            }
        }
    }

}