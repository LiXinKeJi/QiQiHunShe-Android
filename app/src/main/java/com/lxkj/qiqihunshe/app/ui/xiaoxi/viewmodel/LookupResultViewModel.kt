package com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel

import android.content.Intent
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
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.AddFriendActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.adapter.SearchResultAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.databinding.ActivityLookupResultBinding
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/3/1
 */
class LookupResultViewModel : BaseViewModel() {


    var bind: ActivityLookupResultBinding? = null
    var adapter: SearchResultAdapter? = null
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1
    var tag = 0
    var params = HashMap<String, String>()
    var doPositon = -1

    private var flag = -1//3选择牵手人

    fun init(tag: Int, params: HashMap<String, String>) {
        flag = activity!!.intent.getIntExtra("flag", -1)

        this.tag = tag
        this.params = params
        bind?.xRecyclerView?.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader)
        bind?.xRecyclerView?.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin)
        bind?.xRecyclerView?.defaultRefreshHeaderView // get default refresh header view
            ?.setRefreshTimeVisible(true)
        bind?.xRecyclerView?.layoutManager = GridLayoutManager(fragment?.context, 1)
        bind?.xRecyclerView?.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                bind?.xRecyclerView?.setNoMore(false)
                page = 1

                when (tag) {
                    0 -> findUserExact()
                    1 -> findUserCondition()
                    2 -> findUserEconomic()
                }
            }

            override fun onLoadMore() {
                if (page >= totalPage) {
                    bind?.xRecyclerView?.setNoMore(true)
                    return
                }
                page++

                when (tag) {
                    0 -> findUserExact()
                    1 -> findUserCondition()
                    2 -> findUserEconomic()
                }
            }
        })
        adapter = SearchResultAdapter(activity, list)

        adapter?.setOnAddListener {
            if (flag == 3) {//返回选择的牵手人
                EventBus.getDefault().post(list[it])
                val intent = Intent()
                activity!!.setResult(204, intent)
                activity?.finish()
            } else {
                doPositon = it
                var bundle = Bundle()
                bundle.putSerializable("model", list[it])
                MyApplication.openActivityForResult(activity, AddFriendActivity::class.java, bundle, 0)
            }
        }

        bind?.xRecyclerView?.adapter = adapter

        when (tag) {
            0 -> findUserExact()
            1 -> findUserCondition()
            2 -> findUserEconomic()
        }

    }

    //精确查找
    fun findUserExact() {
        params["cmd"] = "findUserExact"
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, XxModel::class.java)
                    bind?.xRecyclerView?.refreshComplete()
                    bind?.xRecyclerView?.setLoadingMoreEnabled(false)
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


    //条件查找
    fun findUserCondition() {
        params["cmd"] = "findUserCondition"
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


    //经济查找
    fun findUserEconomic() {
        params["cmd"] = "findUserEconomic"
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