package com.lxkj.qiqihunshe.app.ui.xiaoxi

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.databinding.FragmentXiaoxiBinding
import kotlinx.android.synthetic.main.fragment_xiaoxi.*

/**
 * Created by Slingge on 2019/2/16
 */
class XiaoXiFragment : BaseFragment<FragmentXiaoxiBinding, XiaoXiViewModel>() {


    override fun getBaseViewModel() = XiaoXiViewModel()

    override fun getLayoutId() = R.layout.fragment_xiaoxi

    override fun init() {

        radio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_0) {//婚社
                rb_0.setBackgroundResource(R.drawable.button_false60)
                rb_1.setTextColor(resources.getColor(R.color.colorTabText))
                rb_1.setBackgroundColor(resources.getColor(R.color.transparent))

                viewModel?.setFragment0()
            } else {//通讯
                rb_1.setTextColor(resources.getColor(R.color.white))
                rb_1.setBackgroundResource(R.drawable.button_false60)
                rb_0.setBackgroundColor(resources.getColor(R.color.transparent))

                viewModel?.setFragment1()
            }
        }

        viewModel?.let {
            it.bind = binding
            it.framanage = childFragmentManager
            it.initBind()
        }

    }

    override fun loadData() {
    }


}