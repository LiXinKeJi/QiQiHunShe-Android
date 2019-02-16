package com.lxkj.qiqihunshe.app.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.shouye.*
import com.lxkj.qiqihunshe.databinding.ActivityMainBinding

/**
 * Created by Slingge on 2019/2/16
 */
class MainViewModel : BaseViewModel() {

    var bind: ActivityMainBinding? = null
    var framanage: FragmentManager? = null

    val shouYeFragment by lazy { ShouYeFragment() }
    val fuJinFragment by lazy { FuJinFragment() }
    val xiaoXiFragment by lazy { XiaoXiFragment() }
    val quYuFragment by lazy { QuYuFragment() }
    val mineFragment by lazy { MineFragment() }

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
        switchFragment(shouYeFragment)
        bind!!.RadioGBottem.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.tab_0 -> {
                    switchFragment(shouYeFragment)
                }
                R.id.tab_1 -> {
                    switchFragment(fuJinFragment)
                }
                R.id.tab_2 -> {
                    switchFragment(xiaoXiFragment)
                }
                R.id.tab_3 -> {
                    switchFragment(quYuFragment)
                }
                R.id.tab_4 -> {
                    switchFragment(mineFragment)
                }
            }
        }
    }


}