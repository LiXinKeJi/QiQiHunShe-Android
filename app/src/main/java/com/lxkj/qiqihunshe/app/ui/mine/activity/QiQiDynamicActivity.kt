package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.QiQiDynamicViewModel
import com.lxkj.qiqihunshe.databinding.ActivityQiqiDynamicBinding
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/21
 */
class QiQiDynamicActivity : BaseActivity<ActivityQiqiDynamicBinding, QiQiDynamicViewModel>() {


    override fun getBaseViewModel() = QiQiDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_qiqi_dynamic
    var page=0

    override fun init() {
        initTitle("七七活动")
        tv_right.visibility= View.VISIBLE
        tv_right.text="报名记录"
        tv_right.setOnClickListener {
            MyApplication.openActivity(this,SignUpRecordAcivity::class.java)
        }

        viewModel?.let {

            it.bind=binding
            it.initViewModel()
        }
    }

    override fun loadData() {
        val json = "{\"cmd\":\"activityList"  +
                "\",\"page\":\"" + page+
                "\"}"
        viewModel!!.getData(json).bindLifeCycle(this)
            .subscribe({}, { toastFailure(it) })
        super.loadData()
    }

}