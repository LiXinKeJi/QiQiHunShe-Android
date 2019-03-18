package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import com.google.gson.Gson
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.MainActivity
import com.lxkj.qiqihunshe.app.ui.dialog.DatePop
import com.lxkj.qiqihunshe.app.ui.entrance.PerfectInfoActivitiy
import com.lxkj.qiqihunshe.app.ui.entrance.model.SignInModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.ReleaseAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.ReleaseInvitationModel
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityReleaseInvitationBinding
import io.reactivex.Single
import org.greenrobot.eventbus.EventBus

/**
 *  发布邀约
 * Created by Slingge on 2019/2/25
 */
class ReleaseInvitationViewModel : BaseViewModel(), ReleaseAdapter.ImageRemoveCallback, DatePop.DateCallBack {


    var bind: ActivityReleaseInvitationBinding? = null

    val ablumList by lazy { ArrayList<LocalMedia>() }
    var imageAdapter: ReleaseAdapter? = null

    val model by lazy { ReleaseInvitationModel() }


    private var dateBirthdayPop: DatePop? = null


    //flag 0我的出生日期
    fun showDate() {
        if (dateBirthdayPop == null) {
            dateBirthdayPop = DatePop(activity, this)
        }
        if (!dateBirthdayPop!!.isShowing) {
            dateBirthdayPop!!.showAtLocation(bind?.llMain, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        }
    }


    override fun position(position1: String, position2: String, position3: String, position4: String, position5: String, position6: String) {
        model.starttime = "$position1-$position2-$position3 $position4:$position5:$position6"
        bind!!.tvTime.text = model.starttime
    }
    override fun position() {
    }

    fun initViewModel() {
        ablumList.add(LocalMedia())
        imageAdapter = ReleaseAdapter(activity!!, ablumList, 9, this)
        imageAdapter?.setFlag(1)
        bind!!.rvAlbum.layoutManager = GridLayoutManager(activity, 3)
        bind!!.rvAlbum.adapter = imageAdapter

    }

    fun setImage(images: List<LocalMedia>) {
        for (i in 0 until images.size) {
            ablumList.add(ablumList.size - 1, images[i])
        }
        imageAdapter!!.notifyDataSetChanged()
    }

    override fun imageRemove(i: Int) {
        ablumList.removeAt(i)
        imageAdapter!!.notifyDataSetChanged()

    }


    fun send(): Single<String> =
        retrofit.getData(Gson().toJson(model))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ToastUtil.showToast("发布成功")
                    EventBus.getDefault().post(EventCmdModel("add", ""))
                    activity?.finish()
                }
            }, activity))


}