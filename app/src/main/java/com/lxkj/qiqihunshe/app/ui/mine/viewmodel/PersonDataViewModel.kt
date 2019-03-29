package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.view.LayoutInflater
import android.widget.TextView
import com.bumptech.glide.load.resource.gif.StreamGifDecoder
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.customview.FlowLayout
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonDataModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentPersonDataBinding
import io.reactivex.Single
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/21
 */
class PersonDataViewModel : BaseViewModel() {

    var userId = ""

    var bind: FragmentPersonDataBinding? = null


    fun getPersonData(): Single<String> {
        val json = "{\"cmd\":\"userData\",\"userId\":\"" + userId + "\"}"
        abLog.e("个人资料",json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, PersonDataModel::class.java)
                bind?.let {
                    it.model = model
                    getLable(it.flBoby, model.interest)
                    getLable(it.flAddress, model.locale)
                }
            }
        }, fragment!!.activity))
    }


    private fun getLable(flType: FlowLayout, array: ArrayList<String>) {

        for (i in 0 until array.size) {
            val tv = LayoutInflater.from(activity).inflate(
                R.layout.layout_flow_talent_type, bind!!.llMain, false
            ) as TextView
            tv.text = array[i]
            flType.addView(tv)
        }
    }


}