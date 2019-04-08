package com.lxkj.qiqihunshe.app.ui.entrance.viewmodel

import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.databinding.FragmentWelcomeBinding

/**
 * Created by Slingge on 2019/4/4
 */
class WelComeFragmentViewModel : BaseViewModel() {


    lateinit var bind: FragmentWelcomeBinding

    var flag = -1

    fun init() {
        when (flag) {
            0 -> bind.image.setImageResource(R.drawable.bg_welcome0)
            1 -> bind.image.setImageResource(R.drawable.bg_welcome1)
            2 -> bind.image.setImageResource(R.drawable.bg_welcome2)
            3 -> bind.image.setImageResource(R.drawable.bg_welcome3)
            4 -> bind.image.setImageResource(R.drawable.bg_welcome4)
            5 -> bind.image.setImageResource(R.drawable.bg_welcome5)
        }
    }


}