package com.lxkj.qiqihunshe.app.ui.mine.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
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


    private val model by lazy { ModifyPassModel() }

    override fun getBaseViewModel() = ModifyPassViewModel()

    override fun getLayoutId() = R.layout.activity_modify_pass

    override fun init() {
        initTitle("修改密码")

        tv_enter.setOnClickListener {
            model.noify()
            ToastUtil.showToast(model.newPass)
        }

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model


        }
    }


}