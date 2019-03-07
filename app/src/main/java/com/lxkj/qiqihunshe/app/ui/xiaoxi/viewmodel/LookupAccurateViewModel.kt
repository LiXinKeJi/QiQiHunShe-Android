package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.databinding.ObservableField
import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupAccurateBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/3/1
 */
class LookupAccurateViewModel:BaseViewModel() {


    val phone = ObservableField<String>()
    var bind: FragmentLookupAccurateBinding? = null





}