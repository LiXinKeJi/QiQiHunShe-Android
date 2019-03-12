package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.fujin.viewmodel.ChatViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.databinding.ActivityChatDetailsBinding
import kotlinx.android.synthetic.main.activity_chat_details.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/3/12
 */
class ChatActivity : BaseActivity<ActivityChatDetailsBinding, ChatViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() = ChatViewModel()

    override fun getLayoutId() = R.layout.activity_chat_details

    override fun init() {

        tv_right.visibility=View.VISIBLE
        tv_right.setOnClickListener(this)
        AbStrUtil.setDrawableLeft(this, R.drawable.yh_ziliao, tv_right, 0)

        iv_del.setOnClickListener(this)
        iv_yuejian.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_del -> {
                iv_del.visibility=View.GONE
                tv_tip0.visibility=View.GONE
            }
            R.id.iv_yuejian -> {

            }
        }
    }

}