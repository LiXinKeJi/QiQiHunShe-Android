package com.lxkj.qiqihunshe.app.ui.xiaoxi

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.CommunicationFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.HunSheFragment
import com.lxkj.qiqihunshe.databinding.FragmentXiaoxiBinding

/**
 * Created by Slingge on 2019/2/16
 */
class XiaoXiViewModel : BaseViewModel() {

    val hunsheFragment by lazy { HunSheFragment() }
    val communicationFragment by lazy { CommunicationFragment() }

    var bind: FragmentXiaoxiBinding? = null
    var framanage: FragmentManager? = null


    private var mFragment = Fragment()

    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mFragment) {
            val transaction = framanage!!.beginTransaction()
            if (!fragment.isAdded) { // 先判断是否被add过
                transaction.hide(mFragment).add(R.id.containers, fragment).commit() // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mFragment).show(fragment).commit() // 隐藏当前的fragment，显示下一个
            }
            mFragment = fragment
        }
    }


    fun initBind() {
        switchFragment(hunsheFragment)
    }


    fun setFragment0() {
        switchFragment(hunsheFragment)
    }

    fun setFragment1() {
        switchFragment(communicationFragment)
    }

}