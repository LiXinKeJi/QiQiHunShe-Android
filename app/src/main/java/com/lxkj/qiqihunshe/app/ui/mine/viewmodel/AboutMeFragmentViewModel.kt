package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.ProgressStyle
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.activity.PersonalInfoActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.AboutMeAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import org.greenrobot.eventbus.EventBus

/**
 * Created by kxn on 2019/3/6 0006.
 */
class AboutMeFragmentViewModel : BaseViewModel() {
    var bind: ActivityXrecyclerviewBinding? = null

    var adapter: AboutMeAdapter? = null
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1
    var type = "1"

    fun init(type: String) {
        this.type = type
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
        adapter = AboutMeAdapter(fragment?.context, list)
        adapter?.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString("userId", list[it].userId)
            MyApplication.openActivity(fragment?.activity, PersonalInfoActivity::class.java, bundle)

        }

        bind?.xRecyclerView?.adapter = adapter
        getList()
    }



    //获取列表
    fun getList() {
        var params = HashMap<String, String>()
        params["cmd"] = "loveOrLook"
        params["uid"] = StaticUtil.uid
        params["type"] = type
        params["page"] = page.toString()
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
                    EventBus.getDefault().post(model)
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