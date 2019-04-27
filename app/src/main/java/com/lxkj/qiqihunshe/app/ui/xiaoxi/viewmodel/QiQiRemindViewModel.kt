package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyBillActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.MyDynamicActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.ReputationBaoActivity
import com.lxkj.qiqihunshe.app.ui.mine.activity.WalletActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.MsgDetailsActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.XqHintAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding

/**
 * Created by Slingge on 2019/3/1
 */
class QiQiRemindViewModel : BaseViewModel() {


    var bind: ActivityXrecyclerviewBinding? = null

    var adapter: XqHintAdapter? = null
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1

    fun init() {
        bind?.xRecyclerView?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        bind?.xRecyclerView?.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin)
        bind?.xRecyclerView?.defaultRefreshHeaderView // get default refresh header view
            ?.setRefreshTimeVisible(true)
        bind?.xRecyclerView?.layoutManager = LinearLayoutManager(fragment?.context)
        bind?.xRecyclerView?.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                bind?.xRecyclerView?.setNoMore(false)
                page = 1
                getMsgList()
            }

            override fun onLoadMore() {
                if (page >= totalPage) {
                    bind?.xRecyclerView?.setNoMore(true)
                    return
                }
                page++
                getMsgList()
            }
        })
        adapter = XqHintAdapter(activity, list)
        adapter?.setOnItemClickListener {
            abLog.e("item", Gson().toJson(list))
            when (list[it].title) {
                "打赏通知" -> MyApplication.openActivity(activity, WalletActivity::class.java)
                "退款提醒" -> MyApplication.openActivity(activity, MyBillActivity::class.java)
                "信息超时" -> RongYunUtil.toChat(activity!!, list[it].userId, list[it].nickname, 0)//临时消息超时
                "约见提醒" -> RongYunUtil.toChat(activity!!, list[it].userId, list[it].nickname, 2)//约见，应该是约会中

                "举报通知" -> {
                    val bundle = Bundle()
                    bundle.putString("userId", StaticUtil.uid)
                    MyApplication.openActivity(activity, ReputationBaoActivity::class.java, bundle)
                }
                "点赞通知" -> {
                     val bundle = Bundle()
                     bundle.putString("id", list[it].objId)
                     MyApplication.openActivity(activity, MyDynamicActivity::class.java, bundle)
                 }
                "评论通知" -> {
                   val bundle = Bundle()
                   bundle.putString("id", list[it].objId)
                   MyApplication.openActivity(activity, MyDynamicActivity::class.java, bundle)
               }
                else -> {
                    val bundle = Bundle()
                    bundle.putSerializable("model", list[it])
                    MyApplication.openActivity(activity, MsgDetailsActivity::class.java, bundle)
                }
            }
        }

        bind?.xRecyclerView?.adapter = adapter

        getMsgList()
    }

    //获取消息列表
    @SuppressLint("CheckResult")
    fun getMsgList() {
        val params = HashMap<String, String>()
        params["cmd"] = "msgList"
        params["uid"] = StaticUtil.uid
        params["page"] = page.toString()

        abLog.e("消息列表",Gson().toJson(params))
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
            }, activity)).subscribe({
                bind?.xRecyclerView?.refreshComplete()
                bind?.xRecyclerView?.loadMoreComplete()
            }, {
                bind?.xRecyclerView?.refreshComplete()
                bind?.xRecyclerView?.loadMoreComplete()
            })
    }


}