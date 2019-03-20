package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle.BallSpinFadeLoader
import com.jcodecraeer.xrecyclerview.ProgressStyle.SquareSpin
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.adapter.HistoryAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityMatchHistoryBinding
import io.rong.imkit.RongIM

/**
 * Created by Slingge on 2019/2/26
 */
class MatchingHistoryViewModel : BaseViewModel() {

    var adapter: HistoryAdapter? = null

    var bind: ActivityMatchHistoryBinding? = null

    var type = 1//	1聊 2语
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1


    var flag = -1//2问题匹配

    fun init() {
        bind?.xRecyclerView?.setRefreshProgressStyle(BallSpinFadeLoader)
        bind?.xRecyclerView?.setLoadingMoreProgressStyle(SquareSpin)
        bind?.xRecyclerView?.defaultRefreshHeaderView // get default refresh header view
            ?.setRefreshTimeVisible(true)
        bind?.xRecyclerView?.layoutManager = GridLayoutManager(fragment?.context, 1)
        bind?.xRecyclerView?.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                bind?.xRecyclerView?.setNoMore(false)
                page = 1
                getPipeiLog()
            }

            override fun onLoadMore() {
                if (page >= totalPage) {
                    bind?.xRecyclerView?.setNoMore(true)
                    return
                }
                page++
                getPipeiLog()
            }
        })

        adapter = HistoryAdapter(activity, list,flag)
        adapter?.setOnItemClickListener {
            RongIM.getInstance().startPrivateChat(fragment?.activity, list[it].userId, list[it].nickname)
        }

        bind?.xRecyclerView?.adapter = adapter

        getPipeiLog()
    }


    //获取消息列表
    @SuppressLint("CheckResult")
    fun getPipeiLog() {
        var params = HashMap<String, String>()
        params["cmd"] = "pipeiLog"
        params["uid"] = StaticUtil.uid
        params["type"] = type.toString()
        params["page"] = page.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, ShouYeModel::class.java)
                    totalPage = model.totalPage.toInt()
                    bind?.xRecyclerView?.refreshComplete()
                    bind?.xRecyclerView?.loadMoreComplete()

                    if (page == 1)
                        list.clear()
                    list.addAll(model.dataList)
                    adapter?.notifyDataSetChanged()

                }
            }, activity)).subscribe({
                bind?.xRecyclerView?.refreshComplete()
                bind?.xRecyclerView?.loadMoreComplete()
            }, {
                bind?.xRecyclerView?.refreshComplete()
                bind?.xRecyclerView?.loadMoreComplete()
            })
    }


}