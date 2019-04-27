package com.lxkj.qiqihunshe.app.rongrun.message

import android.content.Context
import android.support.v7.widget.CardView
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.baidu.mapapi.model.LatLng


import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import io.rong.imkit.model.ProviderTag
import io.rong.imkit.model.UIMessage
import io.rong.imkit.widget.provider.IContainerItemProvider
import org.greenrobot.eventbus.EventBus
import com.baidu.mapapi.utils.DistanceUtil
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.rongrun.model.ShiYueModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.weigan.loopview.OnItemSelectedListener
import java.text.DecimalFormat


/***
 * 定位成功
 * */
@ProviderTag(messageContent = CustomizeMessage5::class,showPortrait = false,centerInHorizontal=true)
class CustomizeMessageItemProvider5(private val context: Context) :
    IContainerItemProvider.MessageProvider<CustomizeMessage5>() {

    private val list by lazy { ArrayList<String>() }

    private var yuejianId = ""
    private var isFirst = false

    private var isDown = false//是否划分过失约

    override fun newView(context: Context, viewGroup: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_custom_message1, null)
        val holder = ViewHolder()
        holder.fl_main = view.findViewById(R.id.fl_main)
        holder.tv_tip = view.findViewById(R.id.tv_tip)

        holder.cardView2 = view.findViewById(R.id.cardView2)
        holder.line0 = view.findViewById(R.id.line0)
        holder.line1 = view.findViewById(R.id.line1)
        holder.line0!!.visibility = View.VISIBLE
        holder.line1!!.visibility = View.VISIBLE

        holder.tv_num = view.findViewById(R.id.tv_num)

        holder.tv_msg = view.findViewById(R.id.tv_msg)
        holder.tv_no = view.findViewById(R.id.tv_no)

        holder.tv_yes = view.findViewById(R.id.tv_yes)
        holder.tv_no!!.text = "小七协助"
        holder.tv_yes!!.text = "导航"


        if (list.isEmpty()) {
            list.add("失约")
            list.add("我方失约")
            list.add("对方失约")
        }

        holder.tv_shiyue = view.findViewById(R.id.tv_shiyue)
        holder.tv_shiyue!!.visibility = View.VISIBLE
        holder.iv_right = view.findViewById(R.id.iv_right)
        holder.iv_right!!.visibility = View.VISIBLE
        holder.sp_shiyue = view.findViewById(R.id.sp_shiyue)
        holder.sp_shiyue!!.adapter = ArrayAdapter(context, R.layout.item_spinner_text_9sp, list)
        holder.sp_shiyue!!.visibility = View.VISIBLE
        isFirst = false

        holder.tv_address = view.findViewById(R.id.tv_address)

        holder.tv_selectAdd = view.findViewById(R.id.tv_selectAdd)

        view.tag = holder
        return view
    }

    override fun bindView(view: View, i: Int, shopMessage: CustomizeMessage5, message: UIMessage) {
        val holder = view.tag as ViewHolder
        holder.tv_msg!!.text = "定位成功"
        holder.tv_address!!.visibility = View.VISIBLE
        holder.tv_address!!.text = "${shopMessage.address}   ${getDistance(shopMessage.lat, shopMessage.lon)}km"
        holder.cardView2!!.visibility = View.VISIBLE

        holder.tv_num!!.text = "4"

        if (!isDown || !message.message.receivedStatus.isDownload) {
            holder.sp_shiyue!!.onItemSelectedListener =
                object : OnItemSelectedListener, AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(index: Int) {

                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if (!isFirst) {
                            isFirst = true
                            return
                        }
                        if (position == 1) {
                            holder.tv_shiyue!!.text = "我方失约"
                        } else if (position == 2) {
                            holder.tv_shiyue!!.text = "对方失约"
                        }
                        EventBus.getDefault().post(ShiYueModel(yuejianId, (position + 1).toString()))

                        isDown=true
                        holder.sp_shiyue!!.onItemSelectedListener=null
                        RongYunUtil.setMessageStatus(message.message.messageId)
                    }
                }
        }

        holder.tv_no!!.setOnClickListener {

            EventBus.getDefault().post(EventCmdModel("5", ""))
        }

        holder.tv_yes!!.setOnClickListener {
            val model = EventCmdModel("6", "")
            model.lat = shopMessage.lat
            model.lon = shopMessage.lon
            EventBus.getDefault().post(model)//导航

        }
        yuejianId = shopMessage.yuejianId
    }

    override fun getContentSummary(shopMessage: CustomizeMessage5): Spannable {
        return SpannableString("内容摘要")
    }

    override fun onItemClick(view: View, i: Int, shopMessage: CustomizeMessage5, uiMessage: UIMessage) {

    }

    internal inner class ViewHolder {
        var fl_main: FrameLayout? = null
        var cardView2: CardView? = null
        var line0: View? = null
        var line1: View? = null
        var tv_num: TextView? = null
        var tv_tip: TextView? = null

        var tv_msg: TextView? = null
        var tv_no: TextView? = null
        var tv_yes: TextView? = null

        var tv_address: TextView? = null
        var tv_selectAdd: TextView? = null

        var tv_shiyue: TextView? = null
        var sp_shiyue: Spinner? = null
        var iv_right: ImageView? = null
    }


    private fun getDistance(lat: String, lon: String): String {
        val p1LL = LatLng(lat.toDouble(), lon.toDouble())
        val p1LL2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())

        return DecimalFormat("#0.00").format((DistanceUtil.getDistance(p1LL, p1LL2) / 1000)).toString()
    }


}
