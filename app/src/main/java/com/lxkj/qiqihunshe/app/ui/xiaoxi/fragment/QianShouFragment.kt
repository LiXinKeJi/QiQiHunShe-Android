package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.UploadMarryActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.QianShouViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentQianshouBinding
import kotlinx.android.synthetic.main.fragment_qianshou.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * Created by Slingge on 2019/3/1
 */
class QianShouFragment : BaseFragment<FragmentQianshouBinding, QianShouViewModel>(), View.OnClickListener {


    override fun getBaseViewModel() = QianShouViewModel()

    override fun getLayoutId() = R.layout.fragment_qianshou

    override fun init() {

        iv_heHeader.setOnClickListener(this)
        tv_heName.setOnClickListener(this)
        iv_0.setOnClickListener(this)

        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
            it.init()
            it.getQianShou().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }
    }

    override fun loadData() {
        EventBus.getDefault().register(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_heHeader, R.id.tv_heName -> {
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivityForResult(activity, LookupActivity::class.java, bundle, 1)
            }
            R.id.iv_0 -> {
                if (!TextUtils.isEmpty(viewModel?.qianshouId)) {
                    val bundle = Bundle()
                    bundle.putString("id", viewModel?.qianshouId)
                    bundle.putString("flag", viewModel?.flag)
                    MyApplication.openActivity(context, UploadMarryActivity::class.java, bundle)
                } else {
                    ToastUtil.showTopSnackBar(activity, "请选择牵手人")
                }
            }
        }
    }

    @Subscribe
    fun onEvent(model: DataListModel) {
        if (StaticUtil.uid == model.userId) {
            ToastUtil.showTopSnackBar(this, "不能添加自己")
            return
        }
        viewModel?.let {

            it.qianshouId = model.userId
            it.name.set(model.nickname)
            it.icon.set(model.icon)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}