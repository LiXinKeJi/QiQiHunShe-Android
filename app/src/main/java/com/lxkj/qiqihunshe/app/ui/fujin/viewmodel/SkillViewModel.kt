package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.CaiYiCommentAdapter
import com.lxkj.qiqihunshe.app.ui.fujin.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.fujin.model.FuJinModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentSkillBinding

/**
 * Created by Slingge on 2019/2/27
 */
class SkillViewModel : BaseViewModel() {

    var adapter : CaiYiCommentAdapter? = null

    var bind: FragmentSkillBinding? = null
    var model: DataListModel? = null
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1

    fun initViewModel() {
        adapter = CaiYiCommentAdapter(fragment?.context, list)
        bind!!.rvComment.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvComment.adapter = adapter
    }

    fun getCaiyiCommentList(){
        var params = HashMap<String,String>()
        params["cmd"] = "caiyiCommentList"
        params["caiyiId"] = model?.caiyiId.toString()
        params["page"] = page.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, FuJinModel::class.java)
                    totalPage = model.totalPage.toInt()
                    page++
                    bind?.bgRefreshLayout?.endLoadingMore()
                    bind?.bgRefreshLayout?.endRefreshing()

                    if (page == 1)
                        list.clear()

                    list.addAll(model.dataList)
                    adapter?.notifyDataSetChanged()


                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
                bind?.bgRefreshLayout?.endLoadingMore()
                bind?.bgRefreshLayout?.endRefreshing()
            }, {
                bind?.bgRefreshLayout?.endLoadingMore()
                bind?.bgRefreshLayout?.endRefreshing()
            })
    }



    /**
     * 评论才艺
     */
    fun addCaiyiComment(content : String){
        var params = HashMap<String,String>()
        params["cmd"] = "addCaiyiComment"
        params["uid"] = StaticUtil.uid
        params["lon"] = StaticUtil.lng
        params["lat"] = StaticUtil.lat
        params["caiyiId"] = model?.caiyiId.toString()
        params["content"] = content
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showTopSnackBar(activity, "评论成功！")
                    bind?.etComment?.setText("")
                    bind?.etComment?.clearFocus()
                    page = 1
                    getCaiyiCommentList()
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {
            })
    }

    /**
     * 播放记录才艺
     */
    fun playCaiyi(){
        var params = HashMap<String,String>()
        params["cmd"] = "playCaiyi"
        params["uid"] = StaticUtil.uid
        params["caiyiId"] = model?.caiyiId.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {
            })
    }



}