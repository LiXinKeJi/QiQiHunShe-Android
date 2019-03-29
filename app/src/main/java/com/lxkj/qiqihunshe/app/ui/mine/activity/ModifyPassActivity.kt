package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.TextUtils
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.model.ModifyPassModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ModifyPassViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityModifyPassBinding
import kotlinx.android.synthetic.main.activity_modify_pass.*

/**
 * 修改支付、登录密码
 * Created by Slingge on 2019/2/20
 */
class ModifyPassActivity : BaseActivity<ActivityModifyPassBinding, ModifyPassViewModel>() {


    override fun getBaseViewModel() = ModifyPassViewModel()

    override fun getLayoutId() = R.layout.activity_modify_pass


    override fun init() {
        initTitle("修改密码")

        viewModel?.let {
            binding.viewmodel = it
            binding.model = it.model
            it.flag = intent.getIntExtra("flag", 0)
            it.phone = intent.getStringExtra("phone")
            it.code = intent.getStringExtra("code")
            it.init()
        }

        tv_enter.setOnClickListener {
            viewModel?.let {
                it.model.noify()
                if (it.flag == 1) {
                    if (TextUtils.isEmpty(it.model.oldPass)) {
                        ToastUtil.showTopSnackBar(this, "请输入新密码")
                        return@setOnClickListener
                    }
                    if (TextUtils.isEmpty(it.model.newPass)) {
                        ToastUtil.showTopSnackBar(this, "请确认新密码")
                        return@setOnClickListener
                    }

                    if (it.model.newPass != it.model.oldPass) {
                        ToastUtil.showTopSnackBar(this, "密码不一致")
                        return@setOnClickListener
                    }
                } else {
                    if (TextUtils.isEmpty(it.model.oldPass)) {
                        ToastUtil.showTopSnackBar(this, "请输入当前密码")
                        return@setOnClickListener
                    }
                    if (TextUtils.isEmpty(it.model.newPass)) {
                        ToastUtil.showTopSnackBar(this, "请输入新密码")
                        return@setOnClickListener
                    }
                }
                viewModel!!.modify().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }

    }


}