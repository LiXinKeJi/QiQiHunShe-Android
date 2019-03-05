package com.lxkj.qiqihunshe.app.ui.quyu.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.ui.quyu.viewmodel.DdtjViewModel
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityDdtjBinding
import kotlinx.android.synthetic.main.activity_ddtj.*
import kotlinx.android.synthetic.main.layout_infowindow_fwqy.view.*
import com.baidu.mapapi.map.Overlay
import com.baidu.mapapi.map.CircleOptions
import com.lxkj.qiqihunshe.app.MyApplication


/**
 * Created by kxn on 2019/3/1 0001.
 */
class DdtjActivity : BaseActivity<ActivityDdtjBinding,DdtjViewModel>() {

    val mMapView by lazy { bmapView.map }

    override fun getBaseViewModel(): DdtjViewModel = DdtjViewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_ddtj
    }

    override fun init() {
        initTitle("地点推荐")
        lat = intent.getDoubleExtra("lat", 0.0).toString()
        lng = intent.getDoubleExtra("lng", 0.0).toString()

        val ll = LatLng(lat.toDouble(), lng.toDouble())
        val builder = MapStatus.Builder()
        builder.target(ll).zoom(15.0f)
        mMapView.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))

        val position = LatLng(lat.toDouble(), lng.toDouble())

        //构建Marker图标
        val des = BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_red)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(position)
            .icon(des)
        //在地图上添加Marker，并显示

         mMapView.addOverlay(option)


        //构造CircleOptions对象
        val mCircleOptions = CircleOptions().center(position)
            .radius(2000)
            .fillColor(R.color.map_round) //填充颜色
        //在地图上显示圆
        val mCircle = mMapView.addOverlay(mCircleOptions)



        addOverlay(LatLng(lat.toDouble() + 0.01, lng.toDouble() - 0.01))
        addOverlay(LatLng(lat.toDouble() - 0.01, lng.toDouble() + 0.01))


    }

    fun addOverlay(point: LatLng) {
        val image = LayoutInflater.from(this).inflate(R.layout.layout_shop_map, null)
        //构建Marker图标
        val des = BitmapDescriptorFactory.fromView(image)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(LatLng(point.latitude - 0.002, point.longitude))
            .icon(des)
        //在地图上添加Marker，并显示

        val marker = mMapView.addOverlay(option) as Marker



        mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                if (p0 == marker) {
                    val view =
                        LayoutInflater.from(this@DdtjActivity)
                            .inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_fwqy, null)
                    view.tvNavigation.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(p0: View?) {
                            ToastUtil.showToast("去导航！")
                        }
                    })
                    val mInfoWindow = InfoWindow(view, point, -100)
                    //使InfoWindow生效
                    mMapView.showInfoWindow(mInfoWindow)
                    MyApplication.openActivity(this@DdtjActivity, ShopDetailActivity::class.java)

                }
                return true
            }

        })

    }
}
