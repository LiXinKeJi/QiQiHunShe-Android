package com.lxkj.qiqihunshe.app.ui.quyu.viewmodel

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseModel
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.RetrofitService
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.quyu.model.DataListModel
import com.lxkj.qiqihunshe.app.ui.quyu.model.QuYuModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.FragmentQuyuBinding
import io.reactivex.Single
import kotlinx.android.synthetic.main.layout_imageview.view.*
import kotlinx.android.synthetic.main.layout_infowindow_qy.view.*

/**
 * Created by Slingge on 2019/2/16
 */
class QuYuViewModel : BaseViewModel() {

    var bind: FragmentQuyuBinding? = null

    var headOffice = DataListModel() //总公司
    var serviceOffice = DataListModel() //服务网点
    var hiList = ArrayList<String>() //打招呼用语

    //获取服务网点
    fun getServiceArea(json: String): Single<String> =
        retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, QuYuModel::class.java)

                    if (!StringUtil.isEmpty(model.arrivalTime)) {
                        bind!!.tvTime?.text = ("到场时间：" + model.arrivalTime)
                        bind!!.tvAqxz.visibility = VISIBLE
                    } else
                        bind!!.tvAqxz.visibility = GONE

                    var servicePosition = 0
                    for (i in 0 until model.dataList?.size) {
                        if (model.dataList[i].default == ("1")) //总公司
                            headOffice = model.dataList[i]

                        if (i > 0 && model.dataList[i].distance.toDouble() < model.dataList[i - 1].distance.toDouble())
                            servicePosition = i



                    }
                    //最近的服务网点
                    serviceOffice = model.dataList[servicePosition]

                    if (null != serviceOffice) {
                        if (serviceOffice.distance.toInt() > 10000)
                            bind!!.tvNoRange.visibility = VISIBLE
                        else
                            bind!!.tvNoRange.visibility = GONE

                        setData(serviceOffice)
                    } else
                        bind!!.tvNoRange.visibility = VISIBLE

                }
            }, fragment?.activity))

    //获取打招呼用语
    fun getChatList(json: String): Single<String> = retrofit.getData(json)
        .async()
        .compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, QuYuModel::class.java)
                for (i in 0 until model.dataList?.size) {
                    hiList.add(model.dataList[i].content)
                }
            }
        }, fragment?.activity))

    //打招呼
    fun greet(json: String): Single<String> = retrofit.getData(json)
        .async()
        .compose(SingleCompose.compose(object : SingleObserverInterface {
            override fun onSuccess(response: String) {
                val model = Gson().fromJson(response, BaseModel::class.java)
                ToastUtil.showTopSnackBar(fragment,model.resultNote)
            }
        }, fragment?.activity))


    fun setData(data: DataListModel) {
        bind?.bmapView?.map!!.clear()
        GlideUtil.glideLoad(fragment!!.context, data?.logo, bind?.headOfficeIv)
        addOverlay(data)
    }


    fun addOverlay(data: DataListModel) {
        val point = LatLng(data.lat.toDouble(), data.lon.toDouble())
        val image = LayoutInflater.from(fragment?.context).inflate(R.layout.layout_imageview, null)
        GlideUtil.glideHeaderLoad(fragment?.context, data?.logo, image?.ivHead)
        //构建Marker图标
        val des = BitmapDescriptorFactory.fromView(image)
        //构建MarkerOption，用于在地图上添加Marker
        val option = MarkerOptions()
            .position(point)
            .icon(des)
        //在地图上添加Marker，并显示
        var mMapView = bind?.bmapView?.map
        val marker = mMapView?.addOverlay(option) as Marker

        //构造CircleOptions对象
        val mCircleOptions = CircleOptions().center(point)
            .radius(10000)//单位米
            .fillColor(R.color.map_round) //填充颜色
        //在地图上显示圆
        mMapView.addOverlay(mCircleOptions)



        mMapView.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
            override fun onMarkerClick(p0: Marker?): Boolean {
                if (p0 == marker) {
                    val view =
                        LayoutInflater.from(fragment?.context)
                            .inflate(com.lxkj.qiqihunshe.R.layout.layout_infowindow_qy, null)
                    view?.tvAddress?.text = "地址：" + data?.address
                    view?.tvPhone?.text = "电话：" + data?.phone
                    view?.tvDistance?.text = "距离：" + DisplayUtil.distanceFormat(data?.distance.toDouble())

                    view.tvNavigation.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(p0: View?) {
                            var mapNavigationUtil = MapNavigationUtil(fragment?.context)
                            mapNavigationUtil?.goToBaiduMap(data.lat, data.lon, data.address)
                        }
                    })
                    val mInfoWindow = InfoWindow(view, point, -100)
                    //使InfoWindow生效
                    mMapView.showInfoWindow(mInfoWindow)
                }
                return true
            }
        })
    }


}