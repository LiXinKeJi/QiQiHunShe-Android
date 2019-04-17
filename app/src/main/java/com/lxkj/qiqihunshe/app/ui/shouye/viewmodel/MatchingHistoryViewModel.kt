package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle.BallSpinFadeLoader
import com.jcodecraeer.xrecyclerview.ProgressStyle.SquareSpin
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.shouye.adapter.HistoryAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.MatchingModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.ShouYeModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityMatchHistoryBinding
import io.reactivex.Single
import io.rong.imkit.RongIM

/**
 * Created by Slingge on 2019/2/26
 */
class MatchingHistoryViewModel : BaseViewModel() {

    var adapter: HistoryAdapter? = null

    var bind: ActivityMatchHistoryBinding? = null

    var type = -1//	1聊 2语，只有聊进入片p2p，其他进入个人资料
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
                if (flag == 2) {
                    peiResult().subscribe({}, { toastFailure(it) })
                } else {
                    getPipeiLog()
                }
            }

            override fun onLoadMore() {
                if (page >= totalPage) {
                    bind?.xRecyclerView?.setNoMore(true)
                    return
                }
                page++
                if (flag == 2) {
                    peiResult().subscribe({}, { toastFailure(it) })
                } else {
                    getPipeiLog()
                }
            }
        })

        adapter = HistoryAdapter(activity, list, flag,type)
        adapter?.setOnItemClickListener {
            if (type == 1) {
                RongYunUtil.toChat(
                    activity!!, list[it].userId, list[it].nickname
                )
            } else {
                val bundle = Bundle()
                bundle.putString("userId", list[it].userId)
                MyApplication.openActivity(activity, PersonalInfoActivity::class.java, bundle)
            }
        }

        bind?.xRecyclerView?.adapter = adapter

    }


    @SuppressLint("CheckResult")
    fun getPipeiLog() {
        var params = HashMap<String, String>()
        params["cmd"] = "pipeiLog"
        params["uid"] = StaticUtil.uid
        params["type"] = type.toString()
        params["page"] = page.toString()
        abLog.e("匹配结果", Gson().toJson(params))
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


    //匹配结果
    @SuppressLint("CheckResult")
    fun peiResult(): Single<String> {
        return retrofit.getData(Gson().toJson(activity!!.intent.getSerializableExtra("model") as MatchingModel))
            .async().compose(SingleCompose.compose(object : SingleObserverInterface {
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
            }, activity))
    }


}