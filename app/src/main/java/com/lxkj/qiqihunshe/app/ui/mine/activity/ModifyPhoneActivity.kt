package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.TextUtils
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.ModifyPassModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ModifyPhoneViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityModifyPhoneBinding
import kotlinx.android.synthetic.main.activity_modify_phone.*

/**
 * 修改支付、登录密码
 * Created by Slingge on 2019/2/20
 */
class ModifyPhoneActivity : BaseActivity<ActivityModifyPhoneBinding, ModifyPhoneViewModel>() {


    private val model by lazy { ModifyPassModel() }

    override fun getBaseViewModel() = ModifyPhoneViewModel()

    override fun getLayoutId() = R.layout.activity_modify_phone

    override fun init() {
        initTitle("修改绑定手机号")

        tv_enter.setOnClickListener {
            model.noify()
            ToastUtil.showToast(model.newPass)
        }
        tv_getcode.setOnClickListener {
            viewModel?.let {
                if (TextUtils.isEmpty(it.phone.get())) {
                ToastUtil.showTopSnackBar(this,"请输入新手机号")
                    return@setOnClickListener
                }
                viewModel?.getCode()
            }

        }

        viewModel?.let {
            binding.viewmodel = it

        }
    }


}