package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.HunSheViewModel
import com.lxkj.qiqihunshe.databinding.FragmentHunsheBinding
import kotlinx.android.synthetic.main.fragment_hunshe.*

/**
 * Created by Slingge on 2019/2/28
 */
class HunSheFragment : BaseFragment<FragmentHunsheBinding, HunSheViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = HunSheViewModel()

    override fun getLayoutId() = R.layout.fragment_hunshe

    override fun init() {

        iv_cancel.setOnClickListener(this)
        rb_xiangshi.setOnClickListener(this)
        rb_yuehui.setOnClickListener(this)
        rb_qianshou.setOnClickListener(this)
        rb_xiangshi.isChecked = true
    }

    override fun loadData() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_cancel -> {
                tv_tip.visibility = View.GONE
                line1.visibility = View.GONE
                iv_cancel.visibility = View.GONE
            }
            R.id.rb_yuehui -> {
                rb_yuehui.isChecked = true
                rb_qianshou.isChecked = false
                rb_xiangshi.isChecked = false
            }
            R.id.rb_qianshou -> {
                rb_yuehui.isChecked = false
                rb_qianshou.isChecked = true
                rb_xiangshi.isChecked = false
            }
            R.id.rb_xiangshi -> {
                rb_yuehui.isChecked = false
                rb_qianshou.isChecked = false
                rb_xiangshi.isChecked = true
            }
        }

    }


}