package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.EditText
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
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityXrecyclerviewBinding
import io.reactivex.Single
import org.json.JSONObject

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


    fun zan(position: Int): Single<String> {
        val json =
            "{\"cmd\":\"zanDongtai\",\"dongtaiId\":\"${list[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\"}"
        abLog.e("json", json)
        return retrofit.getData(json).async()
            .doOnSubscribe {
                if (list[position].zan == "0") {// 0未点赞 1已点
                    list[position].zanNum = (list[position].zanNum.toInt() + 1).toString()
                    list[position].zan = "1"
                } else {
                    list[position].zanNum = (list[position].zanNum.toInt() - 1).toString()
                    list[position].zan = "0"
                }
                adapter!!.notifyDataSetChanged()
            }
    }


    fun jubao(content: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiReport\",\"dongtaiId\":\"${list[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"content\":\"${content}\"}"
        abLog.e("举报", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(fragment!!.activity, "举报提交成功")
            }
        }, fragment!!.activity))
    }

    fun dashang(money: String, position: Int): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiTip\",\"dongtaiId\":\"${list[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"money\":\"$money\"}"
        abLog.e("打赏订单号", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putDouble("money", money.toDouble())
                bundle.putString("num", obj.getString("orderId"))
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(fragment!!.activity, PayActivity::class.java, bundle, 201)
            }
        }, fragment!!.activity))
    }


    //评论
    fun sendComment(position: Int, str: String): Single<String> {
        val json =
            "{\"cmd\":\"addDongtaiComment\",\"dongtaiId\":\"${list[position].dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"lat\":\"${StaticUtil.lat}\",\"content\":\"$str\"" +
                    ",\"lon\":\"${StaticUtil.lng}\"}"
        abLog.e("json", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                list[position].commentNum = (list[position].commentNum.toInt() + 1).toString()
                adapter?.notifyDataSetChanged()
            }
        }, fragment!!.activity))
    }


}