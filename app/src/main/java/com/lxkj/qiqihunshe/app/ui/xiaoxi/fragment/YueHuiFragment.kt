package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.FindUserRelationshipModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.YueHuiVieModel
import com.lxkj.qiqihunshe.databinding.ActivityRecyvlerviewBinding
import kotlinx.android.synthetic.main.activity_recyvlerview.*
import org.greenrobot.eventbus.Subscribe
import java.util.ArrayList

/**
 * Created by Slingge on 2019/3/1
 */
class YueHuiFragment : BaseFragment<ActivityRecyvlerviewBinding, YueHuiVieModel>() {


    override fun getBaseViewModel() = YueHuiVieModel()

    override fun getLayoutId() = R.layout.activity_recyvlerview


    override fun init() {
        include.visibility = View.GONE
        refresh.isEnabled = false
        viewModel?.let {
            it.bind = binding
            it.friendUserList.addAll((arguments?.getSerializable("list") as ArrayList<FindUserRelationshipModel.dataModel>))
            it.initViewmodel()
        }
    }

    override fun loadData() {
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
        }
    }


}