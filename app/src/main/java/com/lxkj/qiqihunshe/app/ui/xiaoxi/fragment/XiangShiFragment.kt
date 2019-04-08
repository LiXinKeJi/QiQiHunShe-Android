package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.mine.activity.InteractiveNotificationActivity
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.QiQiRemindActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.XiangShiViewModel
import com.lxkj.qiqihunshe.databinding.FraXiangshiBinding
import io.rong.imkit.RongIM
import kotlinx.android.synthetic.main.fra_xiangshi.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * Created by Slingge on 2019/2/28
 */
class XiangShiFragment : BaseFragment<FraXiangshiBinding, XiangShiViewModel>(), View.OnClickListener {

    override fun getBaseViewModel() = XiangShiViewModel()

    override fun getLayoutId() = R.layout.fra_xiangshi

    override fun init() {
        include.visibility = View.GONE
        viewModel?.let {
            it.bind = binding
            viewModel?.getNewMsg()!!.bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            it.friendUserList.clear()
            it.friendUserList.addAll((arguments?.getSerializable("list") as ArrayList<FindUserRelationshipModel.dataModel>))
            it.init()
        }

        llHint.setOnClickListener(this)
        llNotify.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel?.getNewMsg()
    }

    override fun loadData() {
        EventBus.getDefault().register(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.llHint -> { //小七提醒
                MyApplication.openActivity(activity, QiQiRemindActivity::class.java)
                tvHintMessageNum.setBackgroundColor(activity!!.resources.getColor(R.color.white))
            }
            R.id.llNotify -> { //互动通知
                viewModel?.let {
                    it.redMsg().subscribe({}, { toastFailure(it) })
                }
                tvNotificationNum.setBackgroundColor(activity!!.resources.getColor(R.color.white))
                MyApplication.openActivity(activity, InteractiveNotificationActivity::class.java)
            }
        }
    }


    @Subscribe
    fun onEvent(model: EventCmdModel) {
        if (!isVisibleToUser) {
            return
        }
        when (model.cmd) {
            "DelMsg" -> {
                viewModel?.let {
                    if (it.messageAdapter.getList()[model.res.toInt()].relationship == "0") {// 0:临时，1:相识，2:约会,3:牵手,4:拉黑
                        it.removeCall(model.lat, model.res.toInt())
                    } else {
                        it.jiechu(model.lat, model.res.toInt())
                    }
                }
            }
            "item" -> {
                ItemCount=model.res.toInt()
                RongYunUtil.toChat(
                    activity!!,
                    viewModel!!.messageAdapter.getList()[model.res.toInt()].userId,
                    viewModel!!.messageAdapter.getList()[model.res.toInt()].nickname,
                    viewModel!!.messageAdapter.getList()[model.res.toInt()].relationship.toInt()
                )
            }
            "xiangshi" -> {//进入相识模式
                viewModel?.let {
                    it.messageAdapter.getList()[ItemCount].relationship="1"
                    it.messageAdapter.notifyItemChanged(ItemCount)
                }
            }
        }
    }

    private var ItemCount = -1

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}