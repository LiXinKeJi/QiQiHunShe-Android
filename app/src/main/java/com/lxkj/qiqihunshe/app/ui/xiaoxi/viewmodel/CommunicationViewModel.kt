package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.quyu.model.QuYuModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.NewPeopleAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FraCommunicationBinding
import io.reactivex.Single
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.fra_communication.*

/**
 * Created by Slingge on 2019/2/28
 */
class CommunicationViewModel : BaseViewModel() {

    lateinit var bind: FraCommunicationBinding

    var adapter: NewPeopleAdapter? = null
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
                getFriendList()
            }

            override fun onLoadMore() {
                if (page >= totalPage) {
                    bind?.xRecyclerView?.setNoMore(true)
                    return
                }
                page++
                getFriendList()
            }
        })
        adapter = NewPeopleAdapter(fragment?.context, list)
        adapter?.setOnItemClickListener {
            RongYunUtil.toChat(fragment!!.activity as Activity, list[it].userId, list[it].nickname, 5)
        }

        adapter?.setOnItemDeleteListener {
            delFriend(it)
        }
        bind?.xRecyclerView?.adapter = adapter

        getFriendList()
    }


    //查看新朋友
    fun newFriends(): Single<String> {
        val json = "{\"cmd\":\"newFriends\",\"uid\":\"" + StaticUtil.uid + "\",\"page\":\"" + 1 + "\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, XxModel::class.java)
                if (TextUtils.isEmpty(model.count) && model.count.toInt() > 0) {
                    bind.tvMesNum.visibility = View.VISIBLE
                }
            }
        }, fragment!!.activity))
    }


    //获取好友
    fun getFriendList() {
        var params = HashMap<String, String>()
        params["cmd"] = "friendList"
        params["uid"] = StaticUtil.uid
        params["page"] = page.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, XxModel::class.java)
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


    //删除好友
    fun delFriend(position: Int) {
        var params = HashMap<String, String>()
        params["cmd"] = "delFriend"
        params["uid"] = StaticUtil.uid
        params["userId"] = list[position].userId
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    list.removeAt(position)
                    adapter?.notifyDataSetChanged()
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {
            })
    }


}