package com.lxkj.qiqihunshe.app.ui.shouye.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.animation.LinearInterpolator
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.MatchingViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityMatchingBinding
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.activity_matching.*

/**
 * Created by Slingge on 2019/2/26
 */
class MatchingActivity : BaseActivity<ActivityMatchingBinding, MatchingViewModel>() {


    override fun getBaseViewModel() = MatchingViewModel()
    override fun getLayoutId() = R.layout.activity_matching

    private var anim: ObjectAnimator? = null


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun init() {
        initTitle("")
        anim = ObjectAnimator.ofFloat(iv_saomiao, "rotation", 0f, 360f)
        anim?.interpolator = LinearInterpolator()//保持匀速
        anim?.duration = 3000//动画持续时间
        anim?.repeatCount = ValueAnimator.INFINITE//循环
        anim?.start()
        tv_match.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("flag", intent.getIntExtra("flag", -1))
            MyApplication.openActivity(this, MatchingHistoryActivity::class.java, bundle)
        }

        viewModel?.let {
            binding.viewmodel = it
        }


        iv_header.setOnClickListener {
            if (TextUtils.isEmpty(viewModel!!.id)) {
                ToastUtil.showTopSnackBar(this, "暂时未匹配到用户")
                return@setOnClickListener
            }
            RongIM.getInstance().startPrivateChat(this, viewModel!!.id, viewModel!!.username)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onStart() {
        super.onStart()
        val flag = intent!!.getIntExtra("flag", -1)
        viewModel!!.headerUrl.set(StaticUtil.headerUrl)
        when (flag) {
            0 -> {
                viewModel?.type = "1"
                viewModel!!.randomUser().bindLifeCycle(this).subscribe({
                    anim?.pause()
                }, { toastFailure(it) })
            }
            1 -> {
                viewModel?.type = "2"
                viewModel!!.randomUser().bindLifeCycle(this).subscribe({
                    anim?.pause()
                }, { toastFailure(it) })
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        anim?.let {
            if (it.isRunning) {
                it.end()
            }
            it.cancel()
        }
    }


}

