package com.lxkj.qiqihunshe.app.ui.entrance

import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.entrance.model.RegisterModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.RegisterViewModel
import com.lxkj.qiqihunshe.app.util.Md5Util
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by Slingge on 2019/2/18
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() =
        RegisterViewModel( )

    override fun getLayoutId() = R.layout.activity_register

    private val model by lazy { RegisterModel() }


    override fun init() {

        initTitle("注册用户")

        tv_getCode.setOnClickListener(this)
        tv_enter.setOnClickListener(this)

        tv_agree.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
        tv_agree.paint.isAntiAlias = true//抗锯齿

        viewModel?.let {
            binding.model = model
            binding.viewmodel = it
            it.bind = binding
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_getCode -> {
                model.notif()
                if (TextUtils.isEmpty(model.phone)) {
                    ToastUtil.showTopSnackBar(this, "请输入手机号")
                    return
                }
                viewModel?.getCode()
                val json = "{\"cmd\":\"sendSms\",\"phone\":\"" + model.phone + "\"}"
                viewModel!!.getcode(json).bindLifeCycle(this).subscribe()
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

                if (TextUtils.isEmpty(model.problem)) {
                    ToastUtil.showTopSnackBar(this, "请输入验证问题")
                    return
                }

                if (TextUtils.isEmpty(model.answer)) {
                    ToastUtil.showTopSnackBar(this, "请输入验证答案")
                    return
                }

                if(!rb_agree.isChecked){
                    ToastUtil.showTopSnackBar(this, "请阅读并同意服务协议")
                    return
                }

                val json = "{\"cmd\":\"userRegist\",\"phone\":\"" + model.phone +
                        "\",\"password\":\"" + Md5Util.md5Encode(model.pass) +
                        "\",\"validate\":\"" + model.code + "\",\"question\":\"" + model.problem +
                        "\",\"answer\":\"" + model.answer + "\"}"
                viewModel!!.register(json).bindLifeCycle(this).subscribe()
            }
        }
    }


}