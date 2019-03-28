package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.os.Build
import android.text.TextUtils
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.DemandListViewModel5
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StatusBarBlackWordUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityDemandList5Binding
import kotlinx.android.synthetic.main.activity_demand_list5.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/3/28
 */
class DemandListActivity5 :
    BaseActivity<ActivityDemandList5Binding, DemandListViewModel5>() {
    override fun getBaseViewModel() = DemandListViewModel5()

    override fun getLayoutId() = R.layout.activity_demand_list5

    override fun init() {
        initTitle("填写需求列表")
        rl_include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setTextColor(resources.getColor(R.color.white))
        AbStrUtil.setDrawableLeft(this,R.drawable.ic_back_w,tv_title,10)

        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }

        viewModel?.let {
            it.bind = binding
            binding.viewmodel = it
            it.init()
            it.getXuqiu().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }


        tv_pay.setOnClickListener {
            val content = AbStrUtil.etTostr(et_content)
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showTopSnackBar(this, "请输入需求")
                return@setOnClickListener
            }
            viewModel?.let {
                it.model.content = content
                it.BuyPer().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == 303) {
            val intent = Intent()
            setResult(5, intent)
            finish()
        }
    }

}