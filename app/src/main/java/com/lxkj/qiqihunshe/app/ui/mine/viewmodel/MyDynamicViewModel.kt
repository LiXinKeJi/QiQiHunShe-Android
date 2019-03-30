package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import cc.shinichi.library.bean.ImageInfo
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.adapter.CommentAdapter
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ImageAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.CommentModel
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import io.reactivex.Single
import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/25
 */
class MyDynamicViewModel : BaseViewModel() {

    val adapter by lazy { CommentAdapter() }
    val imageAdapter by lazy { ImageAdapter() }

    var model = SpaceDynamicModel.dataModel()

    var page = 1
    var totalPage = 1


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
                SeePhotoViewUtil.toPhotoView(activity, model.images, i)
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
                    totalPage = model.totalPage
                    bind!!.tvComment.text = "最新评论（${model.commentCount}）"
                    if (model.dataList.isEmpty()) {
                        adapter.flag = 1
                        bind!!.rvComment.visibility = View.GONE
                        return
                    }else{
                        bind!!.rvComment.visibility = View.VISIBLE
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


    fun sendComment(str:String,et:EditText): Single<String> {
        val json =
            "{\"cmd\":\"addDongtaiComment\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"lat\":\"${StaticUtil.lat}\",\"content\":\"$str\"" +
                    ",\"lon\":\"${StaticUtil.lng}\"}"
        abLog.e("json", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                page = 1
                adapter.flag = 2
                getComment().subscribe()

                model.commentNum = (model.commentNum.toInt() + 1).toString()

                bind?.let {
                    it.tvNum.text = model.commentNum
                    et.setText("")
                }
            }
        }, activity))
    }


    fun zan(): Single<String> {
        val json = "{\"cmd\":\"zanDongtai\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\"}"
        abLog.e("json", json)
        return retrofit.getData(json).async().doOnSuccess {
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
    }


    fun jubao(content: String): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiReport\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"content\":\"$content\"}"
        abLog.e("举报", json)
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                ToastUtil.showTopSnackBar(activity, "举报提交成功")
            }
        }, activity))
    }

    fun dashang(money: String): Single<String> {
        val json =
            "{\"cmd\":\"dongtaiTip\",\"dongtaiId\":\"${model.dongtaiId}\",\"uid\":\"${StaticUtil.uid}\",\"money\":\"$money\"}"
        return retrofit.getData(json).async().compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val obj = JSONObject(response)
                val bundle = Bundle()
                bundle.putDouble("money", money.toDouble())
                bundle.putString("num", obj.getString("orderId"))
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(activity, PayActivity::class.java, bundle, 202)
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