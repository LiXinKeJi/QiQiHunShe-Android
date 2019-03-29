package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.NearDynamicAdapter
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.NearInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.DynamicAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.DynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinDynamicViewModel : BaseViewModel() {

    var bind: ActivityXrecyclerviewBinding? = null
    var adapter: NearDynamicAdapter? = null
    var list = ArrayList<SpaceDynamicModel.dataModel>()
    var page = 1
    var totalPage = 1

    fun init() {
        bind?.xRecyclerView?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        bind?.xRecyclerView?.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin)
        bind?.xRecyclerView?.defaultRefreshHeaderView // get default refresh header view
            ?.setRefreshTimeVisible(true)
        bind?.xRecyclerView?.layoutManager = GridLayoutManager(fragment?.context, 1)
        bind?.xRecyclerView?.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                bind?.xRecyclerView?.setNoMore(false)
                page = 1
                getList()
            }

            override fun onLoadMore() {
                if (page >= totalPage) {
                    bind?.xRecyclerView?.setNoMore(true)
                    return
                }
                page++
                getList()
            }
        })
        adapter = NearDynamicAdapter(fragment?.context, list)
        adapter?.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putSerializable("bean", list[it])
            MyApplication.openActivity(fragment?.activity, MyDynamicActivity::class.java, bundle)
        }

        bind?.xRecyclerView?.adapter = adapter
    }

    //获取列表
    fun getList() {
        var params = HashMap<String, String>()
        params["cmd"] = "nearbyDongtai"
        params["uid"] = StaticUtil.uid
        params["type"] = "0"
        params["lon"] = StaticUtil.lng
        params["lat"] = StaticUtil.lat
        params["page"] = page.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, SpaceDynamicModel::class.java)
                    totalPage = model.totalPage
                    bind?.xRecyclerView?.refreshComplete()
                    bind?.xRecyclerView?.loadMoreComplete()
                    if (page == 1)
                        list.clear()

                    list.addAll(model.dataList)
                    adapter?.notifyDataSetChanged()

                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
                bind?.xRecyclerView?.refreshComplete()
                bind?.xRecyclerView?.loadMoreComplete()
            }, {
                bind?.xRecyclerView?.refreshComplete()
                bind?.xRecyclerView?.loadMoreComplete()
            })
    }

}