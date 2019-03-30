package com.lxkj.qiqihunshe.app.ui.xiaoxi.fragment

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseFragment
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.xiaoxi.activity.LookupResultActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MapData
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupAccurateViewModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.StringUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.FragmentLookupAccurateBinding
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.fragment_lookup_accurate.*

/**
 * Created by Slingge on 2019/3/1
 */
class LookupAccurateFragment :
    BaseFragment<FragmentLookupAccurateBinding, LookupAccurateViewModel>() {

    override fun getBaseViewModel() = LookupAccurateViewModel()

    override fun getLayoutId() = R.layout.fragment_lookup_accurate

    override fun init() {
        viewModel?.let {
            binding.viewmodel = it
            it.bind = binding
        }
        tv_search.setOnClickListener {
            if (!StringUtil.isEmpty(et_phone.text.toString())) {
                params["uid"] = StaticUtil.uid
                params["phone"] = et_phone?.text.toString()
                var map = MapData()
                map.map = params
                val bundle = Bundle()
                bundle.putString("title", "精确查找")
                bundle.putSerializable("map", map)
                bundle.putInt("flag", arguments!!.getInt("flag"))
                MyApplication.openActivityForResult(activity, LookupResultActivity::class.java, bundle, 1)
            } else
                ToastUtil.showTopSnackBar(activity, "输入手机号/七七账号")

        }

    }

    override fun loadData() {

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 1 && resultCode == 204) {//选择牵手人
            activity!!.finish()
        }
    }


}