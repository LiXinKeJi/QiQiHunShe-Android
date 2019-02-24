package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.RealNameAuthenViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityRealnameAuthenBinding
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
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right->{
               ToastUtil.showToast("保存")
            }
        }
    }


}