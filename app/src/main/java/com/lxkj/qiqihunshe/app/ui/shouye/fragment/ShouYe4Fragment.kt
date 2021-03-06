package com.lxkj.qiqihunshe.app.ui.shouye.fragment

import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.QuestionsAnswersDialog
import com.lxkj.qiqihunshe.app.ui.shouye.activity.MatchingActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.MatchingHistoryActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.StrokeActivity
import com.lxkj.qiqihunshe.app.ui.shouye.model.MatchingModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.VoiceTimeModel
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.ShouYe4ViewModel
import com.lxkj.qiqihunshe.app.util.GetDateTimeUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentShouye4Binding
import kotlinx.android.synthetic.main.fragment_shouye_4.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/2/26
 */
class ShouYe4Fragment : BaseFragment<FragmentShouye4Binding, ShouYe4ViewModel>() {

    var flag = -1
    override fun getBaseViewModel() = ShouYe4ViewModel()
    override fun getLayoutId() = R.layout.fragment_shouye_4

    var matchingModel = MatchingModel()


    override fun init() {
        EventBus.getDefault().register(this)
        flag = arguments!!.getInt("flag", -1)
        when (flag) {
            0 -> {
                iv_bg.setImageResource(R.drawable.bg_liao)
                iv_but.setImageResource(R.drawable.ic_liao)
            }
            1 -> {
                iv_bg.setImageResource(R.drawable.bg_yu)
                iv_but.setImageResource(R.drawable.ic_yu)
            }
            2 -> {
                iv_bg.setImageResource(R.drawable.bg_pei)
                iv_but.setImageResource(R.drawable.ic_pei)
            }
            3 -> {
                iv_bg.setImageResource(R.drawable.bg_hua)
                iv_but.setImageResource(R.drawable.ic_hua)
            }
        }

        iv_but.setOnClickListener {
            when (flag) {
                0 -> {
                    if (StaticUtil.isReal == "0") {// 0未认证 1待审核 2已认证 3认证失败
                        ToastUtil.showTopSnackBar(activity, "请先实名认证")
                        return@setOnClickListener
                    }else if(StaticUtil.isReal == "1"){
                        ToastUtil.showTopSnackBar(activity, "实名认证审核中")
                        return@setOnClickListener
                    }
                    val bundle = Bundle()
                    bundle.putInt("flag", 0)
                    matchingModel.type = "1"///1聊 2语
                    matchingModel.cmd = "randomUser"
                    bundle.putSerializable("model", matchingModel)
                    MyApplication.openActivity(activity, MatchingActivity::class.java, bundle)
                    abLog.e("筛选", Gson().toJson(matchingModel))
                }
                1 -> {
                    if (StaticUtil.isReal == "0") {// 0未认证 1待审核 2已认证 3认证失败
                        ToastUtil.showTopSnackBar(activity, "请先实名认证")
                        return@setOnClickListener
                    }else if(StaticUtil.isReal == "1"){
                        ToastUtil.showTopSnackBar(activity, "实名认证审核中")
                        return@setOnClickListener
                    }
                    viewModel?.let {
                        it.matchingTime().bindLifeCycle(this).subscribe({
                            val model = Gson().fromJson(it, VoiceTimeModel::class.java)
                            val l1 = model.startTime.replace(":", "")//开始时间
                            val l2 = model.endTime.replace(":", "")//结束时间
                            val l0 = GetDateTimeUtil.getHMS().replace(":", "")//当前时间

                            if (l0 in l1..l2) {
                                val bundle = Bundle()
                                bundle.putInt("flag", 1)
                                matchingModel.type = "2"///1聊 2语
                                matchingModel.cmd = "randomUser"
                                bundle.putSerializable("model", matchingModel)
                                MyApplication.openActivity(activity, MatchingActivity::class.java, bundle)
                            } else {
                                ToastUtil.showTopSnackBar(activity, "不在匹配时间")
                            }
                        }, { toastFailure(it) })
                    }
                }
                2 -> {//先判断有没有设置问题
                    if (viewModel?.answer == "0") {
                        QuestionsAnswersDialog.sginUpShow(activity!!)
                    } else {
                        val bundle = Bundle()
                        bundle.putInt("flag", 2)
                        matchingModel.cmd = "peiList"
                        bundle.putSerializable("model", matchingModel)
                        MyApplication.openActivity(activity, MatchingHistoryActivity::class.java, bundle)
                    }
                }
                3 -> {
                    MyApplication.openActivity(activity, StrokeActivity::class.java)
                }
            }
        }
    }

    override fun loadData() {
    }

    override fun onResume() {
        super.onResume()
        if (flag == 2)
            viewModel!!.getPeiList().bindLifeCycle(activity!!).subscribe({ }, { toastFailure(it) })
    }

    @Subscribe
    fun onEvent(mode: MatchingModel) {
        if (mode.flag == flag) {
            matchingModel = mode
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        QuestionsAnswersDialog.diss()
    }


}