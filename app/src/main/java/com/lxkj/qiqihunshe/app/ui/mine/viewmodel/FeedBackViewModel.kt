package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityFeedbackBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/20
 */
class FeedBackViewModel : BaseViewModel(), TextWatcher {


    var bind: ActivityFeedbackBinding? = null

    fun initViewModel() {
        bind!!.etContent.addTextChangedListener(this)
    }


    fun feedBack(content: String): Single<String> {
        val json = "{\"cmd\":\"feedback\",\"uid\":\"" + StaticUtil.uid + "\",\"content\":\"" + content + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("提交成功")
                    activity!!.finish()
                }
            }, activity))
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