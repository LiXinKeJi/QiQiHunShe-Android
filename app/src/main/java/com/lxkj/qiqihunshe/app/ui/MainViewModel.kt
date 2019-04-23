package com.lxkj.qiqihunshe.app.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.dialog.PerfectInfoDialog
import com.lxkj.qiqihunshe.app.ui.fujin.FuJinFragment
import com.lxkj.qiqihunshe.app.ui.mine.MineFragment
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.MineModel
import com.lxkj.qiqihunshe.app.ui.mine.model.ReputationBaoModel
import com.lxkj.qiqihunshe.app.ui.quyu.QuYuFragment
import com.lxkj.qiqihunshe.app.ui.shouye.*
import com.lxkj.qiqihunshe.app.ui.xiaoxi.XiaoXiFragment
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityMainBinding
import io.reactivex.Single
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import org.json.JSONObject

/**
 * Created by Slingge on 2019/2/16
 */
class MainViewModel : BaseViewModel(), RongIM.OnReceiveUnreadCountChangedListener {


    lateinit var bind: ActivityMainBinding
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
        getUnreadMsg()
        switchFragment(shouYeFragment)
        bind.RadioGBottem.setOnCheckedChangeListener { group, checkedId ->
            getUnreadMsg()
            when (checkedId) {
                R.id.tab_0 -> {
                    if (Build.VERSION.SDK_INT > 19) {
                        StatusBarUtil.setColorNoTranslucent(activity, Color.parseColor("#2d91ff"))
                    }
                    switchFragment(shouYeFragment)
                }
                R.id.tab_1 -> {
                    if (Build.VERSION.SDK_INT > 19) {
                        StatusBarUtil.setColorNoTranslucent(activity, activity!!.resources.getColor(R.color.white))
                    }
                    switchFragment(fuJinFragment)
                }
                R.id.tab_2 -> {
                    if (Build.VERSION.SDK_INT > 19) {
                        StatusBarUtil.setColorNoTranslucent(activity, activity!!.resources.getColor(R.color.white))
                    }
                    switchFragment(xiaoXiFragment)
                }
                R.id.tab_3 -> {
                    if (Build.VERSION.SDK_INT > 19) {
                        StatusBarUtil.setColorNoTranslucent(activity, activity!!.resources.getColor(R.color.white))
                    }
                    switchFragment(quYuFragment)
                }
                R.id.tab_4 -> {
                    if (Build.VERSION.SDK_INT > 19) {
                        StatusBarUtil.setColorNoTranslucent(activity, activity!!.resources.getColor(R.color.white))
                    }
                    switchFragment(mineFragment)
                }
            }
        }
    }


    //获取未读消息
    fun getUnreadMsg() {
        RongIM.getInstance().getConversationList(object : RongIMClient.ResultCallback<List<Conversation>>() {
            override fun onSuccess(p0: List<Conversation>?) {
                if (p0 == null) {
                    return
                }
                abLog.e("会话列表", Gson().toJson(p0))
                for (im in p0) {
                    if (im.unreadMessageCount > 0) {
                        bind.ivSpot.visibility = View.VISIBLE
                        return
                    }
                }
                bind.ivSpot.visibility = View.GONE
            }

            override fun onError(p0: RongIMClient.ErrorCode?) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "获取会话列表错误 ${p0?.message}")
            }
        })

    }


    //是否违规，违规强制去缴纳信誉金
    fun getUserCredit(): Single<String> {
        val json = "{\"cmd\":\"getUserCredit\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {

                }
            }, activity))
    }

    //获取平台信誉金
    fun getReputationMoney(): Single<String> {
        val json = "{\"cmd\":\"getBail\"" + "}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {

            }
        }, activity))
    }

    //获取信誉金订单号，强制去缴纳
    fun getReputationNum(price:String): Single<String> {
        val json = "{\"cmd\":\"addBailOrder\",\"uid\":\"" + StaticUtil.uid + "\",\"price\":\"" + price + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
            }
        }, activity))
    }


    fun getMine(): Single<String> {
        val json = "{\"cmd\":\"userInfo\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async()
            .doOnSuccess {
                abLog.e("个人信息it", it)
                val model = Gson().fromJson(it, MineModel::class.java)
                abLog.e("个人信息", Gson().toJson(model))

                StaticUtil.headerUrl = model.icon
                StaticUtil.nickName = model.nickname
                SharedPreferencesUtil.putSharePre(activity, "userIcon", model.icon)
                SharedPreferencesUtil.putSharePre(activity, "nickName", model.nickname)

                StaticUtil.isReal = model.auth
                SharedPreferencesUtil.putSharePre(activity, "isAuth", StaticUtil.isReal)

                StaticUtil.headerUrl = model.icon
                StaticUtil.nickName = model.nickname
                StaticUtil.sex = model.sex


                if (TextUtils.isEmpty(StaticUtil.sex)) {    // 0未完善资料 1已完善资料
                    StaticUtil.fill = "0"
                } else {
                    StaticUtil.fill = "1"
                }

                if (StaticUtil.fill == "0") {
                    PerfectInfoDialog.show(activity!!)
                }

                StaticUtil.bail = model.bail

            }
    }


    override fun onMessageIncreased(p0: Int) {

    }


}