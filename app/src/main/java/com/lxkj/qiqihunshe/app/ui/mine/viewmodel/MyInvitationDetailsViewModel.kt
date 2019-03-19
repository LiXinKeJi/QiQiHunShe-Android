package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.MyInvitationDetailsAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.MyInvitationDetailsModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityMyinvitationDetailsBinding
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/25
 */
class MyInvitationDetailsViewModel : BaseViewModel() {

    private val adapterDai by lazy { MyInvitationDetailsAdapter(0) }
    private val adapterNow by lazy { MyInvitationDetailsAdapter(1) }
    private val adapterNo by lazy { MyInvitationDetailsAdapter(2) }

    val daiList = ArrayList<MyInvitationDetailsModel.dataModel>()//待审核
    val nowList = ArrayList<MyInvitationDetailsModel.dataModel>()//当前报名
    val noList = ArrayList<MyInvitationDetailsModel.dataModel>()//拒绝

    val imageAdapter by lazy { ImageAdapter() }

    var model = MyInvitationDetailsModel()

    var yaoyueId = ""//邀约id


    var bind: ActivityMyinvitationDetailsBinding? = null

    fun init() {
        bind!!.rvImage.isFocusable = false
        bind!!.rvImage.layoutManager = GridLayoutManager(fragment?.context, 3)
        bind!!.rvImage.adapter = imageAdapter


        bind!!.rvDai.isFocusable = false
        bind!!.rvDai.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvDai.adapter = adapterDai


        bind!!.rvNow.isFocusable = false
        bind!!.rvNow.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvNow.adapter = adapterNow


        bind!!.rvNo.isFocusable = false
        bind!!.rvNo.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvNo.adapter = adapterNo

    }


    fun getYaoyueDetails(): Single<String> {

        val json = "{\"cmd\":\"yaoyueDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + yaoyueId + "\"}"

        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                model = Gson().fromJson(response, MyInvitationDetailsModel::class.java)
                bind!!.model = model

                imageAdapter.loadMore(model.image, 1)

                val allList = model.dataList

                for (i in 0 until allList.count()) {
                    if (allList[i].type == "0") {//待审核
                        daiList.add(allList[i])
                    } else if (allList[i].type == "1") {//同意
                        nowList.add(allList[i])
                    } else {//2拒绝
                        noList.add(allList[i])
                    }
                }

                bind?.let {
                    it.tvDai.text = "待审核（${daiList.count()}人）"
                    it.tvNow.text = "已同意（${nowList.count()}人）"
                    it.tvNo.text = "已拒绝（${noList.count()}人）"
                }

                if (daiList.isNotEmpty()) {
                    adapterDai.flag = 1
                    adapterDai.upData(daiList)
                } else {
                    bind!!.rvDai.visibility = View.GONE
                }
                if (nowList.isNotEmpty()) {
                    adapterNow.flag = 1
                    adapterNow.upData(nowList)
                } else {
                    bind!!.rvNow.visibility = View.GONE
                }
                if (noList.isNotEmpty()) {
                    adapterNo.flag = 1
                    adapterNo.upData(noList)
                } else {
                    bind!!.rvNo.visibility = View.GONE
                }
            }
        }, activity))
    }

    fun agree(position: Int, type: String, reason: String): Single<String> {
        var joinId = ""
        if (type == "1") {//同意
            joinId = daiList[position].joinId
        } else {
            joinId = nowList[position].joinId
        }

        val json = "{\"cmd\":\"authYaoyue\",\"uid\":\"" + StaticUtil.uid + "\",\"joinId\":\"" + joinId +
                "\",\"type\":\"" + type + "\",\"reason\":\"" + reason + "\"}"

        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                if (type == "1") {
                    daiList[position].type = "1"
                    nowList.add(daiList[position])
                    daiList.removeAt(position)
                    if (daiList.isNotEmpty()) {
                        adapterDai.flag = 1
                        adapterDai.upData(daiList)
                    } else {
                        bind!!.rvDai.visibility = View.GONE
                    }
                    adapterNow.flag = 1
                    adapterNow.upData(daiList)
                } else {
                    nowList[position].type = "2"
                    nowList[position].reason = reason
                    noList.add(daiList[position])
                    nowList.removeAt(position)
                    if (nowList.isNotEmpty()) {
                        adapterNow.flag = 1
                        adapterNow.upData(nowList)
                    } else {
                        bind!!.rvNow.visibility = View.GONE
                    }
                    adapterNo.flag = 1
                    adapterNo.upData(daiList)
                }
            }
        }, activity))
    }


    //删除邀约
    fun DelInvitation(): Single<String> {
        val json =
            "{\"cmd\":\"delYaoyue\",\"uid\":\"" + StaticUtil.uid + "\",\"yaoyueId\":\"" + yaoyueId + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    EventBus.getDefault().post(
                        EventCmdModel(
                            EventBusCmd.DelInvitation,
                            activity!!.intent.getIntExtra("position", -1).toString()
                        )
                    )
                    activity!!.finish()
                }
            }, fragment!!.activity))
    }


}