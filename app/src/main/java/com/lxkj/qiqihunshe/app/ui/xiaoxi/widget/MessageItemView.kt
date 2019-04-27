package com.lxkj.qiqihunshe.app.ui.xiaoxi.widget

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.retrofitnet.*
import com.lxkj.qiqihunshe.app.retrofitnet.exception.dispatchFailure
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonalInfoModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.util.GlideUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import kotlinx.android.synthetic.main.item_message.view.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by Slingge on 2019/2/21
 */
class MessageItemView : RelativeLayout {

    val retrofit by lazy { RetrofitUtil.getRetrofit().create(RetrofitService::class.java) }

    var activity: Activity

    constructor(context: Context, activity: Activity) : super(context) {
        this.activity = activity
    }

    /**
     * 初始化方法
     * */
    init {
        View.inflate(context, R.layout.item_message, this)

    }

    fun setData(bean: FindUserRelationshipModel.dataModel, position: Int) {

        GlideUtil.glideHeaderLoad(context, bean.icon, header)

        tv_title.text = bean.nickname

        tv_age.visibility = View.INVISIBLE

        when (bean.relationship) {// 0:临时，1:相识，2:约会,3:牵手,4:拉黑
            "0" -> tv_type.text = "临时消息"
            "1" -> tv_type.text = "相识消息"
            "2" -> {
                tv_type.text = "约会中"
                if(!TextUtils.isEmpty(bean.realname)){
                    tv_title.text=bean.realname
                }
            }
            "3" -> tv_type.text = "牵手"
        }

        if(bean.newMsgNum>0){
            MyApplication.setRedNum(tv_sysNum, bean.newMsgNum)
        }

        if (bean.yuejian == "1") {
            tv_type.text = "约见中"
        } else if (bean.yuejian == "0") {//新消息
            if(bean.relationship=="0"){
                tv_type.text = "临时消息"
            }
            /*   if (!TextUtils.isEmpty(bean.yuejian) && bean.yuejian.toInt() > 0) {//有新消息

               }
               val json =
                   "{\"cmd\":\"userDetail\",\"uid\":\"" + StaticUtil.uid + "\",\"userId\":\"" + bean.userId + "\"}"
               retrofit.getData(json).async()
                   .doOnSuccess {
                       val model = Gson().fromJson(it, PersonalInfoModel::class.java)
                       if (model.icon.isNotEmpty()) {
                           GlideUtil.glideHeaderLoad(context, model.icon[0], header)
                       }
                       tv_title.text = model.nickname
                   }
                   .subscribe({}, { dispatchFailure(activity, it) })*/
        } else {
            tv_type.text = ""
        }


        btnDelete.setOnClickListener {
            val model = EventCmdModel("DelMsg", position.toString())
            model.lat = bean.userId
            EventBus.getDefault().post(model)
        }

        cl_main.setOnClickListener {
            val model = EventCmdModel("item", position.toString())
            model.lat = bean.userId
            model.res = position.toString()
            EventBus.getDefault().post(model)
            MyApplication.setRedNum(tv_sysNum,0)
        }


    }


}