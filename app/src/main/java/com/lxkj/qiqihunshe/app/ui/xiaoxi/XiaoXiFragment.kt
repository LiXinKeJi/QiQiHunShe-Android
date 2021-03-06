package com.lxkj.qiqihunshe.app.ui.xiaoxi

import android.os.Bundle
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.ScreenMenuPop
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupActivity
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentXiaoxiBinding
import kotlinx.android.synthetic.main.fragment_xiaoxi.*

/**
 * Created by Slingge on 2019/2/16
 */
class XiaoXiFragment : BaseFragment<FragmentXiaoxiBinding, XiaoXiViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() = XiaoXiViewModel()

    override fun getLayoutId() = R.layout.fragment_xiaoxi

    private var categoryPop: ScreenMenuPop? = null

    private var flag = 0//0婚社，1通讯

    override fun init() {

        radio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_0) {//婚社
                rb_0.setBackgroundResource(R.drawable.button_false60)
                rb_1.setTextColor(resources.getColor(R.color.colorTabText))
                rb_1.setBackgroundColor(resources.getColor(R.color.transparent))
                viewModel?.setFragment0()
                flag = 0
            } else {//通讯
                flag = 1
                rb_1.setTextColor(resources.getColor(R.color.white))
                rb_1.setBackgroundResource(R.drawable.button_false60)
                rb_0.setBackgroundColor(resources.getColor(R.color.transparent))
                viewModel?.setFragment1()
            }
        }

        viewModel?.let {
            it.bind = binding
            it.framanage = childFragmentManager
            it.init()
        }

        categoryPop = ScreenMenuPop(activity!!)

        iv_user.setOnClickListener(this)
        iv_search.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_user -> {
                if (flag == 1) {
                    ToastUtil.showTopSnackBar(activity,"此模式不能使用此功能")
                    return
                }
                categoryPop!!.screenPopwindow(iv_user)
            }
            R.id.iv_search -> {
                if (flag == 0) {
                    ToastUtil.showTopSnackBar(activity,"此模式不能使用此功能")
                    return
                }
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(context, LookupActivity::class.java, bundle)
            }
        }
    }


    override fun loadData() {

    }


}