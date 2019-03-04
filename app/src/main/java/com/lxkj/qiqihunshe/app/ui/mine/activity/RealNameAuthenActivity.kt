package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.RealNameAuthenViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRealnameAuthenBinding
import kotlinx.android.synthetic.main.activity_realname_authen.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/20
 */
class RealNameAuthenActivity : BaseActivity<ActivityRealnameAuthenBinding, RealNameAuthenViewModel>(),
    View.OnClickListener {
    override fun getBaseViewModel() = RealNameAuthenViewModel()

    override fun getLayoutId() = R.layout.activity_realname_authen

    override fun init() {
        initTitle("实名认证")
        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"

        tv_right.setOnClickListener(this)

        fl_Id.setOnClickListener(this)
        fl_edu.setOnClickListener(this)
        fl_room.setOnClickListener(this)
        fl_car.setOnClickListener(this)
        fl_salary.setOnClickListener(this)
        fl_answer.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right -> {
                ToastUtil.showToast("保存")
            }
            R.id.fl_Id -> {
                val bundle = Bundle()
                bundle.putInt("flag", 4)
                MyApplication.openActivity(this, UploadCertificatesActivity::class.java, bundle)
            }
            R.id.fl_edu -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(this, UploadCertificatesActivity::class.java, bundle)
            }
            R.id.fl_room -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(this, UploadCertificatesActivity::class.java, bundle)
            }
            R.id.fl_car -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivity(this, UploadCertificatesActivity::class.java, bundle)
            }
            R.id.fl_salary -> {
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivity(this, UploadCertificatesActivity::class.java, bundle)
            }
            R.id.fl_answer -> {
                MyApplication.openActivity(this, QuestionsAuthenActivity::class.java)
            }
        }
    }


}