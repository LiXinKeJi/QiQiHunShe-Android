package com.lxkj.qiqihunshe.app.ui.xiaoxi.activity

import cc.shinichi.sherlockutillibrary.utility.ui.ToastUtil
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.MapData
import com.lxkj.qiqihunshe.app.ui.xiaoxi.viewmodel.LookupResultViewModel
import com.lxkj.qiqihunshe.databinding.ActivityLookupResultBinding

/**
 * Created by Slingge on 2019/3/1
 */
class LookupResultActivity :
    BaseActivity<ActivityLookupResultBinding, LookupResultViewModel>() {

    var mapData = MapData()
    var tag = 0 // 0精确查找 1 条件查找 2 经济匹配

    override fun getBaseViewModel()= LookupResultViewModel ()

    override fun getLayoutId()= R.layout.activity_lookup_result

    override fun init() {
        initTitle(intent.getStringExtra("title"))
        val title = intent.getStringExtra("title")
        tag = when (title) {
            "精确查找" -> 0
            "条件查找" -> 1
            else -> 2
        }

        mapData = intent.getSerializableExtra("map") as MapData


        viewModel?.let {
            it.bind=binding
            it.init(tag, mapData.map)
        }
    }

}