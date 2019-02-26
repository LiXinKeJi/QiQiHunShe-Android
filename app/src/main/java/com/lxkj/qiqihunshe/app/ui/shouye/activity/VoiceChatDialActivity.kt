package com.lxkj.qiqihunshe.app.ui.shouye.activity

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.shouye.viewmodel.VoiceChatDialViewModel
import com.lxkj.qiqihunshe.databinding.ActivityVoiceChatDialBinding

/**
 * 拨打
 * Created by Slingge on 2019/2/26
 */
class VoiceChatDialActivity : BaseActivity< ActivityVoiceChatDialBinding, VoiceChatDialViewModel>() {



    override fun getBaseViewModel() = VoiceChatDialViewModel()

    override fun getLayoutId() = R.layout.activity_voice_chat_dial

    override fun init() {
        initTitle("语音聊天")
    }
}