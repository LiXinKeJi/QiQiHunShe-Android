package com.lxkj.qiqihunshe.app.ui.shouye.fragment

import android.os.Bundle
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.dialog.QuestionsAnswersDialog
import com.lxkj.qiqihunshe.app.ui.shouye.activity.MatchingActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.MatchingHistoryActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.SetupProblemActivity
import com.lxkj.qiqihunshe.app.ui.shouye.activity.StrokeActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.ShouYe4ViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentShouye4Binding
import kotlinx.android.synthetic.main.fragment_shouye_4.*

/**
 * Created by Slingge on 2019/2/26
 */
class ShouYe4Fragment : BaseFragment<FragmentShouye4Binding, ShouYe4ViewModel>() {

    var flag = -1
    override fun getLayoutId() = R.layout.fragment_shouye_4

    override fun init() {
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
                    val bundle = Bundle()
                    bundle.putInt("flag", 0)
                    MyApplication.openActivity(activity, MatchingActivity::class.java, bundle)
                }
                1 -> {
                    val bundle = Bundle()
                    bundle.putInt("flag", 1)
                    MyApplication.openActivity(activity, MatchingActivity::class.java, bundle)
                }
                2 -> {//先判断有没有设置问题
                    if (viewModel?.answer == "0") {
                        QuestionsAnswersDialog.sginUpShow(activity!!)
                    } else {
                        val bundle = Bundle()
                        bundle.putInt("flag", 2)
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
            viewModel?.getPeiList()
    }

    override fun getBaseViewModel() = ShouYe4ViewModel()


    override fun onDestroy() {
        super.onDestroy()
        QuestionsAnswersDialog.diss()
    }
}