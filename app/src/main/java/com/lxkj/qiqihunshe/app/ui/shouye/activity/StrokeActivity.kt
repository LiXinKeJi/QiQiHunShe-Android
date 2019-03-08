package com.lxkj.qiqihunshe.app.ui.shouye.activity

import android.os.Build
import android.view.View
import com.linyuzai.likeornot.LikeOrNotView
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.StrokeViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityStrokeBinding
import kotlinx.android.synthetic.main.activity_stroke.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/26
 */
class StrokeActivity : BaseActivity<ActivityStrokeBinding, StrokeViewModel>() {


    override fun getBaseViewModel() = StrokeViewModel()

    override fun getLayoutId() = R.layout.activity_stroke

    override fun init() {
        initTitle("划一划")
        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.immersiveStatusBar(this, 0f)
            view_staus.visibility = View.VISIBLE
            StatusBarUtil.setStutaViewHeight(this, view_staus)
        }

        rl_include.setBackgroundColor(resources.getColor(R.color.transparent))
        tv_title.setTextColor(resources.getColor(R.color.white))
        tv_title.setBackgroundColor(resources.getColor(R.color.transparent))
        AbStrUtil.setDrawableLeft(this, R.drawable.ic_back_w, tv_title, 10)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.getList()
        }


        like_or_not.onLikeOrNotListener = object : LikeOrNotView.OnLikeOrNotListener {
            override fun onLike(view: View, position: Int) {
                viewModel?.addLove(position,"1")
            }

            override fun onNope(view: View, position: Int) {
                viewModel?.addLove(position,"2")
            }

            override fun onAnimationEnd() {
                //Log.e("onAnimationEnd", "onAnimationEnd");
            }
        }

        nope.setOnClickListener {
            like_or_not.nope()
        }
        like.setOnClickListener {
            like_or_not.like()
        }

    }


}