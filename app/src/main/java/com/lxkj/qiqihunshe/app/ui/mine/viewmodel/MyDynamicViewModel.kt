package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import cc.shinichi.library.bean.ImageInfo
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CommentAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import io.reactivex.Single
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/25
 */
class MyDynamicViewModel : BaseViewModel() {

    val adapter by lazy { CommentAdapter() }
    val imageAdapter by lazy { ImageAdapter() }

    val comment = ObservableField<String>()

    var model = SpaceDynamicModel.dataModel()

    var page = 1
    var totalPage = 1
    private val imageInfoList = ArrayList<ImageInfo>()

    var bind: ActivityMydynamicBinding? = null

    fun initViewModel() {
        bind!!.rvComment.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvComment.adapter = adapter


        bind!!.rvImage.layoutManager = GridLayoutManager(fragment?.context, 3)
        bind!!.rvImage.adapter = imageAdapter

        bind!!.rvImage.addOnItemTouchListener(object : RecyclerItemTouchListener(bind!!.rvImage) {
            override fun onItemClick(vh: RecyclerView.ViewHolder?) {
                val i = vh!!.adapterPosition
                if (i < 0 || i >= model.images.size) {
                    return
                }
                for (j in 0 until model.images.size) {
                    val info = ImageInfo()
                    info.originUrl = model.images[j]
                    info.thumbnailUrl = model.images[i]
                    imageInfoList.add(info)
                }
                SeePhotoViewUtil.toPhotoView(activity, imageInfoList, i)
            }
        })

    }


    fun getComment(): Single<String> {
        val json = "{\"cmd\":\"dongtaiCommentList\",\"dongtaiId\":\"${model.dongtaiId}\",\"page\":\"$page\"}"
        abLog.e("json", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, CommentModel::class.java)
                if (page == 1) {
                    totalPage=model.totalPage
                    bind!!.tvComment.text = "最新评论（${model.commentCount}）"
                    if (model.dataList.isEmpty()) {
                        adapter.flag = 1
                        bind!!.rvComment.visibility = View.GONE
                        return
                    }
                    if (model.totalPage == 1) {
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


    fun sendComment(): Single<String> {
        val json =
            "{\"cmd\":\"addDongtaiComment\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"lat\":\"${StaticUtil.lat}\",\"content\":\"${comment.get()}\"" +
                    ",\"lon\":\"${StaticUtil.lng}\"}"
        abLog.e("json", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                model.commentNum = (model.commentNum.toInt() + 1).toString()
                bind!!.tvNum.text = model.commentNum

                val commModel = CommentModel.dataModel()
                commModel.content = comment.get()!!
                commModel.age = StaticUtil.age
                commModel.distance = "0"
                commModel.adtime = "刚刚"
                commModel.sex = StaticUtil.sex
                commModel.nickname = StaticUtil.nickName
                commModel.icon = StaticUtil.headerUrl

                adapter.addItem(commModel)

                bind!!.rvComment.scrollToPosition(0)
                comment.set("")
            }
        }, activity))
    }


    fun zan(): Single<String> {
        val json = "{\"cmd\":\"zanDongtai\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\"}"

        abLog.e("json", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                bind?.let {
                    if (model.zan == "0") {
                        AbStrUtil.setDrawableLeft(activity!!, R.drawable.ic_zan_hl, it.tvZan, 5)
                        model.zanNum = (model.zanNum.toInt() + 1).toString()
                        model.zan = "1"
                    } else {
                        model.zanNum = (model.zanNum.toInt() - 1).toString()
                        AbStrUtil.setDrawableLeft(activity!!, R.drawable.ic_zan_nor, it.tvZan, 5)
                        model.zan = "0"
                    }
                    it.tvZan.text = model.zanNum
                }
            }
        }, activity))
    }


    fun jubao(content: String): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiReport\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\" ,\"content\":\"${content}\" }"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(activity, "举报提交成功")
            }
        }, activity))
    }

    fun dashang(money: String): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiTip\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\" ,\"money\":\"money\" }"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(activity, "打赏成功")
            }
        }, activity))
    }


    //删除动态
    fun DelDynamuc(position: Int): Single<String> {
        val json =
            "{\"cmd\":\"delDongtai\",\"uid\":\"" + StaticUtil.uid + "\",\"dongtaiId\":\"" + model.dongtaiId + "\"}"
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val intent = Intent()
                    intent.putExtra("position", position)
                    activity!!.setResult(0, intent)
                    activity?.finish()
                }
            }, activity))
    }


}