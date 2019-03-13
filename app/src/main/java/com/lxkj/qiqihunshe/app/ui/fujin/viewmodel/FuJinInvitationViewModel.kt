package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

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
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.NearInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonInvitationDetailsActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.AboutMeAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.PersonInvitationAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.InvitationModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentFujinInvitationBinding

/**
 * Created by Slingge on 2019/2/26
 */
class FuJinInvitationViewModel : BaseViewModel() {
    var typeId = ""
    var bind: FragmentFujinInvitationBinding? = null

    var adapter: NearInvitationAdapter? = null
    var list = ArrayList<DataListModel>()
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
        adapter = NearInvitationAdapter(fragment?.context, list)
        adapter?.setOnItemClickListener {
            ToastUtil.showTopSnackBar(fragment, it.toString())
        }

        bind?.xRecyclerView?.adapter = adapter
    }

    //获取列表
    fun getList() {
        var params = HashMap<String, String>()
        params["cmd"] = "nearbyYaoyue"
        params["uid"] = StaticUtil.uid
        params["typeId"] = typeId
        params["category"] = "0"
        params["lon"] = StaticUtil.lng
        params["lat"] = StaticUtil.lat
        params["page"] = page.toString()
        abLog.e("附近邀约", Gson().toJson(params))
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, XxModel::class.java)
                    totalPage = model.totalPage.toInt()
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