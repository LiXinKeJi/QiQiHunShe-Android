package com.lxkj.qiqihunshe.app.ui.xiaoxi

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.CommunicationFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.HunSheFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.RelationshipModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentXiaoxiBinding
import io.rong.imlib.model.Conversation


/**
 * Created by Slingge on 2019/2/16
 */
class XiaoXiViewModel : BaseViewModel() {

    val hunsheFragment by lazy { HunSheFragment() }
    val communicationFragment by lazy { CommunicationFragment() }

    var bind: FragmentXiaoxiBinding? = null
    var framanage: FragmentManager? = null

    private var title = "相识"

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


    fun init() {
        hunsheFragment.STabSelectCallBack(object : HunSheFragment.TabSelectCallBack {
            override fun select(i: Int) {
                bind?.let {
                    when (i) {
                        0 -> it.tvState.text = "相识"
                        1 -> it.tvState.text = "约会"
                        2 -> it.tvState.text = "牵手"
                    }
                    title = AbStrUtil.tvTostr(it.tvState)
                }
            }
        })
        setFragment0()
    }

    fun setFragment0() {
        bind!!.tvState.text = title
        switchFragment(hunsheFragment)
    }

    fun setFragment1() {
        bind!!.tvState.text = "通讯"
        switchFragment(communicationFragment)
    }

}