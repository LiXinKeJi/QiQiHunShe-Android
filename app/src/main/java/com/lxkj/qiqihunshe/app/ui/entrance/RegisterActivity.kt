package com.lxkj.qiqihunshe.app.ui.entrance

import android.graphics.Paint
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.entrance.model.RegisterModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.RegisterViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

/**
 * Created by Slingge on 2019/2/18
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = RegisterViewModel()

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
                viewModel?.getCode()
            }
            R.id.tv_enter -> {
                model.notif()
                ToastUtil.showTopSnackBar(this, model.code)
                MyApplication.openActivity(this, PerfectInfoActivitiy::class.java)
            }
        }
    }


}