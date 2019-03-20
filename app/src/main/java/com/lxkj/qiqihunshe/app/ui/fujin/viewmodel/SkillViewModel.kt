package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.fujin.adapter.CaiYiCommentAdapter
import com.lxkj.qiqihunshe.app.ui.fujin.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.fujin.model.FuJinModel
import com.lxkj.qiqihunshe.app.ui.mine.activity.PayActivity
import com.lxkj.qiqihunshe.app.ui.mine.model.WalletModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.EventBusCmd
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.FragmentSkillBinding
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * 我的才艺，才艺详情Fragment共用
 * * Created by Slingge on 2019/2/27
 */
class SkillViewModel : BaseViewModel() {

    var adapter: CaiYiCommentAdapter? = null

    var bind: FragmentSkillBinding? = null
    var model: DataListModel? = null
    var list = ArrayList<DataListModel>()
    var page = 1
    var totalPage = 1

    var money = ""//每分钟价格
    var type = 0 //通话类型 0 语音 1 视频

    var cayiId = ""


    fun initViewModel() {
        adapter = CaiYiCommentAdapter(fragment?.context, list)
        bind!!.rvComment.layoutManager = LinearLayoutManager(fragment?.context)
        bind!!.rvComment.adapter = adapter

        getCaiyiCommentList()
    }

    fun getCaiyiCommentList() {
        var params = HashMap<String, String>()
        params["cmd"] = "caiyiCommentList"
        params["caiyiId"] = model?.caiyiId.toString()
        params["page"] = page.toString()
        abLog.e("获取评论", Gson().toJson(params))
        retrofit.getData(Gson().toJson(params))
            .async()
            .doOnSuccess {
                val model = Gson().fromJson(it, FuJinModel::class.java)
                totalPage = model.totalPage
                if (page == 1) {
                    list.clear()
                    bind!!.comment.text="最新评论（${model.commentCount}）"
                }
                list.addAll(model.dataList)
                adapter?.notifyDataSetChanged()
            }
            .bindLifeCycle(fragment!!).subscribe({
            }, {
                toastFailure(it)
            })
    }


    /**
     * 评论才艺
     */
    fun addCaiyiComment(content: String) {
        var params = HashMap<String, String>()
        params["cmd"] = "addCaiyiComment"
        params["uid"] = StaticUtil.uid
        params["lon"] = StaticUtil.lng
        params["lat"] = StaticUtil.lat
        params["caiyiId"] = model?.caiyiId.toString()
        params["content"] = content
        abLog.e("评论", Gson().toJson(params))
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    bind?.etComment?.setText("")
                    bind?.etComment?.clearFocus()
                    page = 1
                    getCaiyiCommentList()
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {
                toastFailure(it)
            })
    }

    /**
     * 播放记录才艺
     */
    fun playCaiyi() {
        var params = HashMap<String, String>()
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
                toastFailure(it)
            })
    }


    //通话计时费用
    fun changefee(json: String): Single<String> {
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    abLog.e("上传通话计时", "json")
                }
            }, fragment!!.activity))
    }


    fun <T> compose(): ObservableTransformer<T, T> {
        return return ObservableTransformer { upstream ->
            upstream.doOnSubscribe {
                ToastUtil.showToast("开始加载")
            }.doOnTerminate {
                ToastUtil.showToast("结束加载")
            }
        }
    }

    //打赏
    fun dashang(money: String): Single<String> {
        val json = "{\"cmd\":\"caiyiTip\",\"uid\":\"" + StaticUtil.uid + "\",\"caiyiId\":\"" + model?.caiyiId +
                "\",\"money\":\"" + money + "\"}"
        abLog.e("打赏", json)
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val obj = JSONObject(response)
                    val bundle = Bundle()
                    bundle.putInt("flag", 0)
                    bundle.putDouble("money", money.toDouble())
                    bundle.putString("num", obj.getString("orderId"))
                    MyApplication.openActivity(fragment!!.activity, PayActivity::class.java, bundle)
                }
            }, fragment!!.activity))
    }


    fun getBannel(): Single<String> {
        val json = "{\"cmd\":\"userBalance\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json).async().doOnSuccess {
            val model = Gson().fromJson(it, WalletModel::class.java)
            StaticUtil.amount = model.amount
        }
    }


    //删除才艺
    fun DelSkill(): Single<String> {
        val json =
            "{\"cmd\":\"delCaiyi\",\"uid\":\"" + StaticUtil.uid + "\",\"caiyiId\":\"" + cayiId + "\"}"
        abLog.e("删除才艺", json)
        return retrofit.getData(json).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    activity?.let {
                        val intent = Intent()
                        intent.putExtra("position", it.intent.getIntExtra("position", -1))
                        it.setResult(303, intent)
                        it.finish()
                    }
                }
            }, activity))
    }


}