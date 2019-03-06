package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.text.TextUtils
import android.util.Log
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.model.ModifyPassModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ModifyPassViewModel
import com.lxkj.qiqihunshe.app.util.Md5Util
import com.lxkj.qiqihunshe.app.util.StaticUtil
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
    var flag=-1
    var phone=""
    var code="" +
            ""

    override fun init() {
        initTitle("修改密码")

        flag=intent.getIntExtra("flag",0)
        phone=intent.getStringExtra("phone")
        code=intent.getStringExtra("code")

        tv_enter.setOnClickListener {
            model.noify()
            if(TextUtils.isEmpty(model.oldPass)){
                ToastUtil.showToast(getString(R.string.current_pwd_isnot_null))
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(model.newPass)){
                ToastUtil.showToast(getString(R.string.new_pwd_isnot_null))
                return@setOnClickListener
            }

//            cmd 	是 	string 	updatePassword
//                    uid 	是 	string 	用户ID
//                    phone 	是 	string 	手机号
//                    validate 	是 	string 	验证码
//                    type 	是 	string 	1登录密码 2支付密码
//            newPassword 	是

            val json = "{\"cmd\":\"updatePassword\",\"phone\":\"" + phone +
                    "\",\"newPassword\":\"" + Md5Util.md5Encode(model.newPass) +
                    "\",\" uid\":\"" + StaticUtil.uid+
                    "\",\"validate\":\"" + code + "\",\"type\":\"" + flag.toString()+
                   "\"}"

            Log.i("sss","modify pwd  json------------------->"+json)

            viewModel!!.modifypwd(json).bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })

           // ToastUtil.showToast(model.newPass)
        }

        viewModel?.let {
            binding.viewmodel = it
            binding.model = model


        }


    }


}