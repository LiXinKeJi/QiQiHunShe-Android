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
import com.lxkj.qiqihunshe.app.rongrun.model.ShiYueModel
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.weigan.loopview.OnItemSelectedListener
import org.greenrobot.eventbus.Subscribe
import java.text.DecimalFormat


/***
 * 消费划分
 * */
@ProviderTag(messageContent = CustomizeMessage6::class,showPortrait = false,centerInHorizontal=true)
class CustomizeMessageItemProvider6(private val context: Context) :
    IContainerItemProvider.MessageProvider<CustomizeMessage6>() {


    private var isRetrieved = false//是否完成消费划分

    private var isYuejian = false//是否完成约见


    override fun newView(context: Context, viewGroup: ViewGroup): View {

        EventBus.getDefault().register(this)

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
        holder.tv_no!!.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel("5", ""))
        }
        holder.tv_yes = view.findViewById(R.id.tv_yes)
        holder.tv_no!!.text = "解除关系"
        holder.tv_yes!!.text = "完成约见"


        holder.tv_address = view.findViewById(R.id.tv_address)

        holder.tv_selectAdd = view.findViewById(R.id.tv_selectAdd)
        holder.tv_selectAdd!!.text = "消费划分"
        holder.tv_selectAdd!!.visibility = View.VISIBLE

        view.tag = holder
        return view
    }

    override fun bindView(view: View, i: Int, shopMessage: CustomizeMessage6, message: UIMessage) {
        val holder = view.tag as ViewHolder
        holder.tv_msg!!.text = "印象评分"
        holder.tv_address!!.visibility = View.VISIBLE
        holder.tv_address!!.text = "${shopMessage.address}   ${getDistance(shopMessage.lat, shopMessage.lon)}km"
        holder.cardView2!!.visibility = View.VISIBLE

        holder.tv_num!!.text = "5"


        holder.tv_yes!!.setOnClickListener {
            //完成预约
            if (isYuejian || message.message.receivedStatus.isDownload) {
                return@setOnClickListener
            }
            EventBus.getDefault().post(EventCmdModel("7", shopMessage.yuejianId))
        }
        holder.tv_no!!.setOnClickListener {
            //解除关系
            if (isYuejian || message.message.receivedStatus.isDownload) {
                return@setOnClickListener
            }
            EventBus.getDefault().post(EventCmdModel("8", shopMessage.yuejianId))
        }

        holder.tv_selectAdd!!.setOnClickListener {
            //消费划分
            if (isRetrieved || message.message.receivedStatus.isRetrieved) {
                return@setOnClickListener
            }
            val model = EventCmdModel("9", shopMessage.yuejianId)
            model.lat = message.message.messageId.toString()
            EventBus.getDefault().post(model)
        }

    }

    override fun getContentSummary(shopMessage: CustomizeMessage6): Spannable {
        return SpannableString("内容摘要")
    }

    override fun onItemClick(view: View, i: Int, shopMessage: CustomizeMessage6, uiMessage: UIMessage) {

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

    }


    private fun getDistance(lat: String, lon: String): String {
        val p1LL = LatLng(lat.toDouble(), lon.toDouble())
        val p1LL2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())

        return DecimalFormat("#0.00").format((DistanceUtil.getDistance(p1LL, p1LL2) / 1000)).toString()
    }


    @Subscribe
    fun onEvent(cmd: String) {
        if (cmd == "isRetrieved") {//已划分消费
            isRetrieved = true
        }else if(cmd=="isYuejian"){
            isYuejian=true
        }
    }

}
