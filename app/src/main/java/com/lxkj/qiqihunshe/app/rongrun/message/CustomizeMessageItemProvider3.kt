package com.lxkj.qiqihunshe.app.rongrun.message

import android.content.Context
import android.support.v7.widget.CardView
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import io.rong.imkit.model.ProviderTag
import io.rong.imkit.model.UIMessage
import io.rong.imkit.widget.provider.IContainerItemProvider
import io.rong.imlib.model.Message
import org.greenrobot.eventbus.EventBus

/***
 * 同意约见
 * */
@ProviderTag(messageContent = CustomizeMessage3::class)
class CustomizeMessageItemProvider3(private val context: Context) :
    IContainerItemProvider.MessageProvider<CustomizeMessage3>() {

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

        holder.tv_selectAdd = view.findViewById(R.id.tv_selectAdd)
        holder.tv_selectAdd!!.setOnClickListener {
            EventBus.getDefault().post(EventBus.getDefault().post(EventCmdModel("3", "")))
        }

        view.tag = holder
        return view
    }

    override fun bindView(view: View, i: Int, shopMessage: CustomizeMessage3, message: UIMessage) {
        val holder = view.tag as ViewHolder

        if (message.messageDirection == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.tv_tip!!.text = "您已同意约见请求，等待对方选择约见地点"
            holder.cardView2!!.visibility = View.GONE
            holder.tv_num!!.visibility = View.GONE
            holder.line0!!.visibility = View.GONE
            holder.line1!!.visibility = View.GONE
        } else {
            holder.tv_msg!!.text = "点击“选择地点”完成约见地点选择"
            holder.tv_num!!.text = "2"
            holder.line0!!.visibility = View.VISIBLE
            holder.line1!!.visibility = View.VISIBLE
            holder.tv_tip!!.visibility = View.GONE
            holder.cardView2!!.visibility = View.VISIBLE
            holder.tv_no!!.visibility = View.INVISIBLE
            holder.tv_yes!!.visibility = View.INVISIBLE
            holder.tv_selectAdd!!.visibility = View.VISIBLE
        }

    }

    override fun getContentSummary(shopMessage: CustomizeMessage3): Spannable {
        return SpannableString("内容摘要")
    }

    override fun onItemClick(view: View, i: Int, shopMessage: CustomizeMessage3, uiMessage: UIMessage) {
        when (view.id) {
            R.id.tv_no -> {

            }
            R.id.tv_yes -> {

            }
        }
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

        var tv_selectAdd: TextView? = null
    }


}
