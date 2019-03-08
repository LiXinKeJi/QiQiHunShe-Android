package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.SpaceDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/25
 */
class SpaceDynamicViewModel : BaseViewModel() {


    private val adapter by lazy { SpaceDynamicAdapter() }

    var page = 1

    var bind: ActivityRecyvlerviewBinding? = null

    fun initViewModel() {
        bind!!.recycler.layoutManager = LinearLayoutManager(fragment?.context)

        bind!!.recycler.adapter = adapter

        adapter.setMyListener { itemBean, position ->
            val bundle = Bundle()
            bundle.putSerializable("bean", itemBean)
            bundle.putInt("flag", 0)
            bundle.putInt("position", adapter.i)
            MyApplication.openActivityForResult(fragment!!.activity, MyDynamicActivity::class.java, bundle,0)
        }
    }


    fun getMyDynamic(): Single<String> {
        val json = "{\"cmd\":\"dongtai\",\"uid\":\"" + StaticUtil.uid + /*"\",\"userId\":\"" + StaticUtil.uid +*/
                "\",\"type\":\"" + "0" + "\",\"page\":\"" + page + "\"}"
        abLog.e("我的动态", json)
        return retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    bind!!.refresh.isRefreshing = false
                    val model = Gson().fromJson(response, SpaceDynamicModel::class.java)
                    if(page>model.totalPage){
                        return
                    }
                    if (page == 1) {
                        if (model.totalPage == 1 || model.dataList.isEmpty()) {
                            adapter.flag = 0
                        }
                        adapter.upData(model.dataList)
                    } else {
                        if (page == model.totalPage) {
                            adapter.loadMore(model.dataList, 0)
                        } else {
                            adapter.loadMore(model.dataList, -1)
                        }
                    }
                }
            }, fragment!!.activity))
    }


    //删除动态
    fun DelDynamuc(position: Int): Single<String> {
        val json =
            "{\"cmd\":\"delDongtai\",\"uid\":\"" + StaticUtil.uid + "\",\"dongtaiId\":\"" + adapter.getList()[position].dongtaiId + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast(position.toString())
                    removeItem(position)
                }
            }, fragment!!.activity))
    }


    fun removeItem(position: Int){
        adapter.removeItem(position)
    }



}