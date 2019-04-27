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
import io.rong.imlib.model.Message
import org.greenrobot.eventbus.EventBus
import com.baidu.mapapi.utils.DistanceUtil
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.rongrun.model.YueJianModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import java.text.DecimalFormat


/***
 * 同意约见
 * */
@ProviderTag(messageContent = CustomizeMessage4::class,showPortrait = false,centerInHorizontal=true)
  class CustomizeMessageItemProvider4(private val context: Context) :
    IContainerItemProvider.MessageProvider<CustomizeMessage4>() {

    override fun onItemClick(p0: View?, p1: Int, p2: CustomizeMessage4?, p3: UIMessage?) {

    }

    private var isDown = false//是否同意或拒绝

    override fun newView(context: Context, viewGroup: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_custom_message1, null)
        val holder = ViewHolder()
        holder.fl_main = view.findViewById(R.id.fl_main)
        holder.tv_tip = view.findViewById(R.id.tv_tip)

        holder.cardView2 = view.findViewById(R.id.cardView2)
        holder.line0 = view.findViewById(R.id.line0)
        holder.line1 = view.findViewById(R.id.line1)

        holder.tv_num = view.findViewById(R.id.tv_num)

        holder.tv_msg = view.findViewById(R.id.tv_msg)
        holder.tv_no = view.findViewById(R.id.tv_no)
        holder.tv_yes = view.findViewById(R.id.tv_yes)

        holder.tv_address = view.findViewById(R.id.tv_address)

        holder.tv_selectAdd = view.findViewById(R.id.tv_selectAdd)

        view.tag = holder
        return view
    }

    override fun bindView(view: View, i: Int, shopMessage: CustomizeMessage4, message: UIMessage) {
        val holder = view.tag as ViewHolder

        if (message.message.receivedStatus.isDownload) {
            setColor(holder)
        }

        if (message.messageDirection == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.tv_tip!!.text = "地址已发送"
            holder.cardView2!!.visibility = View.GONE
            holder.tv_num!!.visibility = View.GONE
            holder.line0!!.visibility = View.GONE
            holder.line1!!.visibility = View.GONE
        } else {
            holder.tv_msg!!.text = "对方发来定位地点"
            holder.tv_address!!.text =
                "${shopMessage.address}   ${getDistance(shopMessage.lat, shopMessage.lon)}km  ${shopMessage.time}"
            holder.tv_address!!.visibility = View.VISIBLE
            holder.tv_num!!.visibility = View.VISIBLE
            holder.tv_num!!.text = "3"
            holder.line0!!.visibility = View.VISIBLE
            holder.line1!!.visibility = View.VISIBLE
            holder.tv_tip!!.visibility = View.GONE
            holder.cardView2!!.visibility = View.VISIBLE
            holder.tv_no!!.visibility = View.VISIBLE
            holder.tv_yes!!.visibility = View.VISIBLE
            holder.tv_selectAdd!!.visibility = View.GONE

            holder.tv_yes!!.setOnClickListener {
                if (isDown || message.message.receivedStatus.isDownload) {
                    return@setOnClickListener
                }
                val model = YueJianModel()
                model.lat = shopMessage.lat
                model.lon = shopMessage.lon
                model.address = shopMessage.address
                model.arrivaltime = shopMessage.time
                EventBus.getDefault().post(model)

                setColor(holder)
                isDown=true
                RongYunUtil.setMessageStatus(message.message.messageId)
            }

            holder.tv_no!!.setOnClickListener {
                if (isDown || message.message.receivedStatus.isDownload) {
                    return@setOnClickListener
                }
                val model = EventCmdModel("4", "6")
                model.lat = shopMessage.address
                EventBus.getDefault().post(model)//拒绝定位

                setColor(holder)
                isDown=true
                RongYunUtil.setMessageStatus(message.message.messageId)
            }

        }

    }


    fun setColor(holder: ViewHolder) {
        holder.tv_no!!.setBackgroundResource(R.drawable.bg_gray_60)
        holder.tv_no!!.setTextColor(context.resources.getColor(R.color.colorTabText))
        holder.tv_yes!!.setBackgroundResource(R.drawable.bg_gray_60)
        holder.tv_yes!!.setTextColor(context.resources.getColor(R.color.colorTabText))
    }

    override fun getContentSummary(shopMessage: CustomizeMessage4): Spannable {
        return SpannableString("内容摘要")
    }


    inner class ViewHolder {
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
    }


    private fun getDistance(lat: String, lon: String): String {
        val p1LL = LatLng(lat.toDouble(), lon.toDouble())
        val p1LL2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())
        return DecimalFormat("#0.00").format((DistanceUtil.getDistance(p1LL, p1LL2) / 1000)).toString()
    }


}
