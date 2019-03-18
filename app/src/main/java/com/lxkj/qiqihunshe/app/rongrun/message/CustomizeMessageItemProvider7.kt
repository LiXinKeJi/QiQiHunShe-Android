package com.lxkj.qiqihunshe.app.rongrun.message

import android.content.Context
import android.support.v7.widget.CardView
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
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
import io.rong.imlib.model.Message
import java.text.DecimalFormat


/***
 * 显示消费划分金额
 * */
@ProviderTag(messageContent = CustomizeMessage7::class)
class CustomizeMessageItemProvider7(private val context: Context) :
    IContainerItemProvider.MessageProvider<CustomizeMessage7>() {


    override fun newView(context: Context, viewGroup: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_custom_message1, null)
        val holder = ViewHolder()
        holder.fl_main = view.findViewById(R.id.fl_main)

        holder.cardView2 = view.findViewById(R.id.cardView2)
        holder.line1 = view.findViewById(R.id.line1)
        holder.line0 = view.findViewById(R.id.line0)

        holder.tv_num = view.findViewById(R.id.tv_num)

        holder.tv_address = view.findViewById(R.id.tv_address)

        holder.tv_msg = view.findViewById(R.id.tv_msg)
        holder.tv_no = view.findViewById(R.id.tv_no)
        holder.tv_no!!.setOnClickListener {
            EventBus.getDefault().post(EventCmdModel("5", ""))
        }
        holder.tv_yes = view.findViewById(R.id.tv_yes)
        holder.tv_no!!.text = "拒绝"
        holder.tv_yes!!.text = "同意"


        holder.tv_tip = view.findViewById(R.id.tv_tip)

        view.tag = holder
        return view
    }

    override fun bindView(view: View, i: Int, shopMessage: CustomizeMessage7, message: UIMessage) {
        val holder = view.tag as ViewHolder

        if (message.messageDirection == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.cardView2!!.visibility = View.GONE
            holder.tv_tip!!.visibility = View.VISIBLE
            holder.tv_tip!!.text = "消费划分已发送"
            holder.line1!!.visibility = View.GONE
            holder.line0!!.visibility = View.GONE
            holder.tv_num!!.visibility = View.GONE
        } else {
            holder.tv_tip!!.visibility = View.GONE
            holder.tv_msg!!.text = "对方发来消费划分"
            holder.tv_address!!.visibility = View.VISIBLE
            if (TextUtils.isEmpty(shopMessage.price)) {
                holder.tv_address!!.text = "对方支付全部消费金额"
            } else {
                holder.tv_address!!.text = "您需要支付金额：￥${shopMessage.price}"
            }

            holder.line1!!.visibility = View.VISIBLE
            holder.line0!!.visibility = View.INVISIBLE

            holder.cardView2!!.visibility = View.VISIBLE

            holder.tv_num!!.text = "6"



            holder.tv_yes!!.setOnClickListener {
                //同意消费划分
                EventBus.getDefault().post(EventBus.getDefault().post(EventCmdModel("10", shopMessage.price)))
            }
            holder.tv_no!!.setOnClickListener {
                //拒绝消费划分
                EventBus.getDefault().post(EventBus.getDefault().post(EventCmdModel("11", shopMessage.price)))
            }
        }

    }

    override fun getContentSummary(shopMessage: CustomizeMessage7): Spannable {
        return SpannableString("内容摘要")
    }

    override fun onItemClick(view: View, i: Int, shopMessage: CustomizeMessage7, uiMessage: UIMessage) {

    }

    internal inner class ViewHolder {
        var fl_main: FrameLayout? = null
        var cardView2: CardView? = null
        var line1: View? = null
        var line0: View? = null
        var tv_num: TextView? = null

        var tv_tip: TextView? = null

        var tv_msg: TextView? = null
        var tv_no: TextView? = null
        var tv_yes: TextView? = null

        var tv_address: TextView? = null

    }


    private fun getDistance(lat: String, lon: String): String {
        val p1LL = LatLng(lat.toDouble(), lon.toDouble())
        val p1LL2 = LatLng(StaticUtil.lat.toDouble(), StaticUtil.lng.toDouble())

        return DecimalFormat("#0.00").format((DistanceUtil.getDistance(p1LL, p1LL2) / 1000)).toString()
    }


}
