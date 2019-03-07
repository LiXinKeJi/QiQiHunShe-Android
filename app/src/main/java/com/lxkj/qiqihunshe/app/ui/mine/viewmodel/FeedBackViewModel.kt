package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityFeedbackBinding

/**
 * Created by Slingge on 2019/2/20
 */
class FeedBackViewModel : BaseViewModel(), TextWatcher {


    var bind: ActivityFeedbackBinding? = null

    fun initViewModel() {
        bind!!.etContent.addTextChangedListener(this)
    }


    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

        }
    }


    override fun afterTextChanged(s: Editable?) {
        val str = s.toString()
        val num = str.length
        ToastUtil.showToast(str)
        if (num > 300) {
            return
        }
        bind!!.tvNum.text = "${num}/300"
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }


}