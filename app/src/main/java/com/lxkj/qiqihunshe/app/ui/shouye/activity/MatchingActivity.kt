package com.lxkj.qiqihunshe.app.ui.shouye.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.view.View
import android.view.animation.LinearInterpolator
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.MatchingViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityMatchingBinding
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.activity_matching.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/26
 */
class MatchingActivity : BaseActivity<ActivityMatchingBinding, MatchingViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = MatchingViewModel()
    override fun getLayoutId() = R.layout.activity_matching

    private var anim: ObjectAnimator? = null

    private var flag = -1

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun init() {
        initTitle("")

        AbStrUtil.setDrawableLeft(this, R.drawable.ic_add3, tv_right, 0)
        tv_right.visibility = View.VISIBLE
        tv_right.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("flag", intent.getIntExtra("flag", -1))
            MyApplication.openActivity(this, MatchingHistoryActivity::class.java, bundle)
        }

        anim = ObjectAnimator.ofFloat(iv_saomiao, "rotation", 0f, 360f)
        anim?.interpolator = LinearInterpolator()//保持匀速
        anim?.duration = 3000//动画持续时间
        anim?.repeatCount = ValueAnimator.INFINITE//循环
        anim?.start()

        viewModel?.let {
            binding.viewmodel = it
            it.headerUrl.set(StaticUtil.headerUrl)
        }

        flag = intent!!.getIntExtra("flag", -1)
        randomUser()
        iv_in.setOnClickListener(this)
        iv_next.setOnClickListener(this)
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_in -> {
                if (TextUtils.isEmpty(viewModel!!.id)) {
                    ToastUtil.showTopSnackBar(this, "暂时未匹配到用户")
                    return
                }
                RongYunUtil.toChat(this, viewModel!!.id, viewModel!!.username)
                finish()
            }
            R.id.iv_next -> {
                randomUser()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun randomUser() {
        if (flag == 0) {
            viewModel?.type = "1"
            viewModel!!.randomUser().bindLifeCycle(this).subscribe({
                anim?.pause()
            }, { toastFailure(it) })
        } else if (flag == 1) {
            viewModel?.type = "2"
            viewModel!!.randomUser().bindLifeCycle(this).subscribe({
                anim?.pause()
            }, { toastFailure(it) })
        }

            anim?.resume()//恢复
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

