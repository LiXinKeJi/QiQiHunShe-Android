package com.lxkj.qiqihunshe.app.retrofitnet

import android.app.Activity
import android.text.TextUtils
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.ui.model.GetTagModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import io.reactivex.Single

/**
 * 获取平台标签
 * Created by Slingge on 2019/3/4
 */
class GetTagUtil(val activity: Activity, val tagListCallback: TagListCallback) {


    public interface TagListCallback {
        fun TagList(tagList: ArrayList<String>)
    }

    val retrofit by lazy { RetrofitUtil.getRetrofitApi().create(RetrofitService::class.java) }

    //type 1情感计划 2我的类型 3兴趣爱好 4地点标签 5薪资范围 6车辆价格 7房屋面积 8学历 9实名认证问题
    //sex 0女 1男
    fun getTag(sex: String, type: String) {
        if(TextUtils.isEmpty(sex)){
            ToastUtil.showTopSnackBar(activity,"请选择性别")
            return
        }
        val model = GetTagModel()
        model.type = type
        model.sex = sex
        gettags(Gson().toJson(model)).subscribe()

    }


    fun gettags(json: String): Single<String> {
        return retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, GetTagModel::class.java)
                    model.dataList?.let {
                        tagListCallback.TagList(model.dataList)
                    }

                }
            }, activity))
    }

}