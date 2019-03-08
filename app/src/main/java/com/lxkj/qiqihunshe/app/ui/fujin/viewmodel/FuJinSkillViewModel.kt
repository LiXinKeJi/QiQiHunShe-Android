package com.lxkj.qiqihunshe.app.ui.fujin.viewmodel

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.fujin.fragment.SkillFragment
import com.lxkj.qiqihunshe.app.ui.fujin.model.FuJinModel
import com.lxkj.qiqihunshe.app.ui.mine.adapter.FragmentPagerAdapter
import com.lxkj.qiqihunshe.app.ui.xiaoxi.model.XxModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.databinding.FragmentFujinSkillBinding
import kotlinx.android.synthetic.main.fragment_fujin_skill.*
import java.util.ArrayList

/**
 * Created by kxn on 2019/3/7 0007.
 */
class FuJinSkillViewModel : BaseViewModel(){
    var page = 1
    var totalPage = 1
    var bind: FragmentFujinSkillBinding? = null
    val list = ArrayList<Fragment>()
    var adapter : FragmentPagerAdapter ? = null
    fun init(){
        adapter = FragmentPagerAdapter(fragment!!.childFragmentManager, list)
        bind?.viewPager?.adapter = adapter
        getNearbyCaiyi()
        bind?.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(p0: Int) {
                    if (p0 == list.size - 1 && page < totalPage){
                        totalPage++
                        getNearbyCaiyi()
                    }
            }
        })
    }

    fun getNearbyCaiyi(){
        var params = HashMap<String,String>()
        params["cmd"] = "nearbyCaiyi"
        params["uid"] = StaticUtil.uid
        params["lon"] = StaticUtil.lng
        params["lat"] = StaticUtil.lat
        params["page"] = page.toString()
        retrofit.getData(Gson().toJson(params))
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, FuJinModel::class.java)
                    totalPage = model.totalPage.toInt()
                    for (i in 0..model.dataList.size) {
                        var bundle = Bundle()
                        bundle.putSerializable("model",model.dataList[i])
                        list.add(Fragment.instantiate(activity, SkillFragment::class.java.name,bundle))
                    }
                   adapter?.notifyDataSetChanged()
                }
            }, fragment?.activity)).bindLifeCycle(fragment!!).subscribe({
            }, {
            })
    }

}