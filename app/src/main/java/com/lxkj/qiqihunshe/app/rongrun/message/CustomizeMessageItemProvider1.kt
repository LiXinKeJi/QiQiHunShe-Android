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
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.ui.model.EventCmdModel
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imkit.model.ProviderTag
import io.rong.imkit.model.UIMessage
import io.rong.imkit.widget.provider.IContainerItemProvider
import io.rong.imlib.model.Message
import org.greenrobot.eventbus.EventBus

@ProviderTag(messageContent = CustomizeMessage1::class,showPortrait = false,centerInHorizontal=true)
class CustomizeMessageItemProvider1(private val context: Context) :
    IContainerItemProvider.MessageProvider<CustomizeMessage1>() {

    private var isDown=false//消息是否已操作

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

        view.tag = holder
        return view
    }

    override fun bindView(view: View, i: Int, shopMessage: CustomizeMessage1, message: UIMessage) {
        val holder = view.tag as ViewHolder

        if (message.message.receivedStatus.isDownload) {
            setColor(holder)
        }

        if (message.messageDirection == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.tv_tip!!.visibility = View.VISIBLE
            holder.tv_tip!!.text = "已发起约见信息，请等待"
            holder.cardView2!!.visibility = View.GONE
            holder.tv_num!!.visibility = View.GONE
            holder.line0!!.visibility = View.GONE
            holder.line1!!.visibility = View.GONE
        } else {
            holder.tv_tip!!.visibility = View.INVISIBLE
            holder.cardView2!!.visibility = View.VISIBLE
            holder.tv_num!!.visibility = View.VISIBLE
            holder.line0!!.visibility = View.VISIBLE
            holder.line1!!.visibility = View.VISIBLE
        }


        holder.tv_no!!.setOnClickListener {
            if (isDown||message.message.receivedStatus.isDownload) {
                return@setOnClickListener
            }
            abLog.e("拒绝请求", "2")
            EventBus.getDefault().post(EventCmdModel("1", ""))
            setColor(holder)
            isDown=true
            RongYunUtil.setMessageStatus(message.message.messageId)
        }
        holder.tv_yes!!.setOnClickListener {
            if (isDown||message.message.receivedStatus.isDownload) {
                return@setOnClickListener
            }
            EventBus.getDefault().post(EventCmdModel("2", ""))
            setColor(holder)
            isDown=true
            RongYunUtil.setMessageStatus(message.message.messageId)
        }

    }


    fun setColor(holder: ViewHolder) {
        holder.tv_no!!.setBackgroundResource(R.drawable.bg_gray_60)
        holder.tv_no!!.setTextColor(context.resources.getColor(R.color.colorTabText))
        holder.tv_yes!!.setBackgroundResource(R.drawable.bg_gray_60)
        holder.tv_yes!!.setTextColor(context.resources.getColor(R.color.colorTabText))
    }

    override fun getContentSummary(shopMessage: CustomizeMessage1): Spannable {
        return SpannableString("内容摘要")
    }


    override fun onItemClick(view: View, i: Int, shopMessage: CustomizeMessage1, uiMessage: UIMessage) {
    }

    class ViewHolder {
        var fl_main: FrameLayout? = null
        var cardView2: CardView? = null
        var line0: View? = null
        var line1: View? = null
        var tv_num: TextView? = null
        var tv_tip: TextView? = null

        var tv_msg: TextView? = null
        var tv_no: TextView? = null
        var tv_yes: TextView? = null
    }


}
