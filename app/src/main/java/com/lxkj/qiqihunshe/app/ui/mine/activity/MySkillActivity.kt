package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.view.View
import cn.jzvd.JzvdStd
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.SkillViewModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import kotlinx.android.synthetic.main.fragment_skill.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/3/7
 */
class MySkillActivity : BaseActivity<com.lxkj.qiqihunshe.databinding.ActivityMyskillBinding, SkillViewModel>() {
    override fun getBaseViewModel() = SkillViewModel()
    override fun getLayoutId() = R.layout.activity_myskill

    override fun init() {
        initTitle("")
        tv_right.visibility = View.VISIBLE
        tv_right.text = "删除"
        tv_right.setOnClickListener {
            viewModel!!.DelSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

        viewModel?.let {
            it.cayiId=intent.getStringExtra("id")
        }

        GlideUtil.glideLoad(
            this,
            intent.getStringExtra("image"),
            jc_video.thumbImageView
        )
        jc_video.setUp(
            intent.getStringExtra("video"),
            "", JzvdStd.SCREEN_WINDOW_NORMAL
        )

    }




}