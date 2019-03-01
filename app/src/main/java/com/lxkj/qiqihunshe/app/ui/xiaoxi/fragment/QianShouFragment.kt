package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.UploadMarryActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.QianShouViewModel
import com.lxkj.qiqihunshe.databinding.FragmentQianshouBinding
import kotlinx.android.synthetic.main.fragment_qianshou.*

/**
 * Created by Slingge on 2019/3/1
 */
class QianShouFragment : BaseFragment<FragmentQianshouBinding, QianShouViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = QianShouViewModel()

    override fun getLayoutId() = R.layout.fragment_qianshou

    override fun init() {

        iv_heHeader.setOnClickListener(this)
        tv_heName.setOnClickListener(this)
        iv_0.setOnClickListener(this)
    }

    override fun loadData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_heHeader, R.id.tv_heName -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(context, LookupActivity::class.java, bundle)
            }
            R.id.iv_0 -> {
                MyApplication.openActivity(context, UploadMarryActivity::class.java)
            }
        }
    }

}