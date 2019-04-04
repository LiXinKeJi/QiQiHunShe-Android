package com.lxkj.qiqihunshe.app.ui.xiaoxi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.CommunicationFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment.HunSheFragment
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.RelationshipModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentXiaoxiBinding
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation


/**
 * Created by Slingge on 2019/2/16
 */
class XiaoXiViewModel : BaseViewModel() {

    val hunsheFragment by lazy { HunSheFragment() }
    val communicationFragment by lazy { CommunicationFragment() }

    var bind: FragmentXiaoxiBinding? = null
    var framanage: FragmentManager? = null


    private val friendUserList by lazy { ArrayList<FindUserRelationshipModel.dataModel>() }

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
                }
            }
        })
    }

    fun setFragment0() {
        val bundle = Bundle()
        bundle.putSerializable("list", friendUserList)
        hunsheFragment.arguments = bundle
        switchFragment(hunsheFragment)
    }

    fun setFragment1() {
        bind!!.tvState.text = "通讯"
        switchFragment(communicationFragment)
    }


    //本地回话列表中的id集合
    private val imList by lazy { ArrayList<String>() }
    private val chatList by lazy { ArrayList<Conversation>() }
    fun getAllIMList() {
        RongIM.getInstance().getConversationList(object : RongIMClient.ResultCallback<List<Conversation>>() {
            override fun onSuccess(p0: List<Conversation>?) {
                if (p0 == null) {
                    setFragment0()
                    return
                }
                chatList.clear()
                chatList.addAll(p0)
                abLog.e("会话列表", Gson().toJson(p0))
                for (im in p0) {
                    imList.add(im.targetId)
                }
                abLog.e("imList", Gson().toJson(imList))
                if (imList.isNotEmpty()) {
                    isFriend()
                }
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "获取会话列表错误 ${p0?.message}")
            }
        })

    }


    fun isFriend() {
        abLog.e("获取我的好友关系", Gson().toJson(RelationshipModel(imList)))
        retrofit.getData(Gson().toJson(RelationshipModel(imList))).async()
            .doOnSuccess {

                val model = Gson().fromJson(it, FindUserRelationshipModel::class.java)
                if (friendUserList.isNotEmpty()) {
                    friendUserList.clear()
                }
                friendUserList.addAll(model.dataList)

                  for (count in 0 until friendUserList.size) {
                      var count = 0
                      for (i in 0 until chatList.size) {
                          if (chatList[i].targetId != friendUserList[count].userId) {
                              if (chatList[count].unreadMessageCount > 0) {
                                  friendUserList[count].newMsgNum = chatList[count].unreadMessageCount//有新消息
                              } else {
                                  friendUserList[count].newMsgNum = 0
                              }
                          }
                      }
                  }

                abLog.e("friendUserList", Gson().toJson(friendUserList))
                setFragment0()
            }
            .bindLifeCycle(fragment!!).subscribe({}, { toastFailure(it) })
    }


}