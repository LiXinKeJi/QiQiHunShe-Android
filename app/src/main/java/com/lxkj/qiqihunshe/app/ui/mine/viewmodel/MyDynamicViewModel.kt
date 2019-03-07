package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CommentAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/25
 */
class MyDynamicViewModel : BaseViewModel() {

    val adapter by lazy { CommentAdapter() }
    val imageAdapter by lazy { ImageAdapter() }


    var page = 1

    var dongtaiId = ""


    var bind: ActivityMydynamicBinding? = null

    fun initViewModel() {
        bind!!.rvComment.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvComment.adapter = adapter


        bind!!.rvImage.layoutManager = GridLayoutManager(fragment?.context, 3)
        bind!!.rvImage.adapter = imageAdapter

    }


    fun getComment(): Single<String> {
        val json = "{\"cmd\":\"dongtaiCommentList\",\"dongtaiId\":\"$dongtaiId\",\"page\":\"$page\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, CommentModel::class.java)
                if (page == 1) {
                    bind!!.tvComment.text = "最新评论（${model.commentCount}）"
                    if (model.dataList.isEmpty() || model.totalPage == 1) {
                        adapter.flag = 0
                    }
                    adapter.upData(model.dataList)
                } else {
                    if (page >= model.totalPage) {
                        adapter.loadMore(model.dataList, 0)
                    } else {
                        adapter.loadMore(model.dataList, -1)
                    }
                }
            }
        }, activity))
    }

}