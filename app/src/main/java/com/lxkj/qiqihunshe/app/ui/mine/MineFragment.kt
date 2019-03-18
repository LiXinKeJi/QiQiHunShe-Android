package com.lxkj.qiqihunshe.app.ui.mine

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.AppConsts
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.mine.activity.*
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MineViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentMineBinding
import io.rong.callkit.RongCallKit
import io.rong.calllib.RongCallClient
import io.rong.imkit.RongIM
import io.rong.imlib.model.CSCustomServiceInfo
import kotlinx.android.synthetic.main.fragment_mine.*


/**
 * Created by Slingge on 2019/2/16
 */
class MineFragment : BaseFragment<FragmentMineBinding, MineViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = MineViewModel()

    override fun getLayoutId() = R.layout.fragment_mine

    override fun init() {

        tv_state.setOnClickListener(this)
        iv_header.setOnClickListener(this)
        tv_editInfo.setOnClickListener(this)
        tv_authent.setOnClickListener(this)

        tv_qiandao.setOnClickListener(this)
        tv_huodong.setOnClickListener(this)
        tv_tuijian.setOnClickListener(this)
        tv_tongzhi.setOnClickListener(this)

        tv_reputation_bao.setOnClickListener(this)
        tv_wallet.setOnClickListener(this)
        tv_rule.setOnClickListener(this)
        tv_space.setOnClickListener(this)
        tv_area.setOnClickListener(this)
        tv_blacklist.setOnClickListener(this)
        tv_service.setOnClickListener(this)
        tv_setup.setOnClickListener(this)

        tv_mylist.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel!!.getMine().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_state -> {//选择
                viewModel?.selectState()
            }
            R.id.tv_editInfo -> {//完善资料
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(activity, PerfectInfoActivitiy::class.java, bundle)
            }
            R.id.tv_authent -> {//实名认证
                if (viewModel!!.auth == "2") {// 实名认证状态 0未认证 1待审核 2已认证 3认证失败
                    ToastUtil.showTopSnackBar(activity,"您已认证通过")
                    return
                }
                if (viewModel!!.auth == "1") {// 实名认证状态 0未认证 1待审核 2已认证 3认证失败
                    ToastUtil.showTopSnackBar(activity,"您的认证正在审核")
                    return
                }
                MyApplication.openActivity(activity, RealNameAuthenActivity::class.java)
            }
            R.id.iv_header -> {//个人主页
                MyApplication.openActivity(activity, PersonalInfoActivity::class.java)
            }
            R.id.tv_qiandao -> {//签到
                MyApplication.openActivity(activity, CheckInActivity::class.java)
            }
            R.id.tv_huodong -> {//七七活动
                MyApplication.openActivity(activity, QiQiDynamicActivity::class.java)
            }
            R.id.tv_tuijian -> {//七七推荐
                MyApplication.openActivity(activity, QiQiRecommendActivity::class.java)
            }
            R.id.tv_tongzhi -> {//互动通知
                MyApplication.openActivity(activity, InteractiveNotificationActivity::class.java)
            }
            R.id.tv_reputation_bao -> {//信誉宝
                MyApplication.openActivity(activity, ReputationBaoActivity::class.java)
            }
            R.id.tv_wallet -> {//钱包
                MyApplication.openActivity(activity, WalletActivity::class.java)
            }
            R.id.tv_rule -> {//七七规则
                MyApplication.openActivity(activity, QiQiRuleActivity::class.java)
            }
            R.id.tv_space -> {//我的空间
                MyApplication.openActivity(activity, MySpaceActivity::class.java)
            }
            R.id.tv_area -> {//情感专区
                MyApplication.openActivity(activity, AffectiveZoneActivity::class.java)
            }
            R.id.tv_blacklist -> {//黑名单
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivity(activity, QiQiBlackListActivity::class.java, bundle)
            }
            R.id.tv_mylist -> {//我的黑名单
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivity(activity, QiQiBlackListActivity::class.java, bundle)
            }
            R.id.tv_service -> {//我的客服
                RongYunUtil.toService(activity)
            }
            R.id.tv_setup -> {//设置
                MyApplication.openActivity(activity, SetUpActivity::class.java)
            }
        }
    }


    override fun loadData() {
    }


}