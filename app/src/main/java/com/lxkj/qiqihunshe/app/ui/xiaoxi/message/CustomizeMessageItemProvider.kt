package com.lxkj.qiqihunshe.app.ui.xiaoxi.message

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.Spannable
import android.text.SpannableString
import android.view.View
import android.view.ViewGroup
import io.rong.imkit.widget.provider.IContainerItemProvider
import android.view.LayoutInflater
import com.lxkj.qiqihunshe.R
import io.rong.imkit.model.UIMessage
import io.rong.imlib.model.Message

/**
 * Created by Slingge on 2019/3/14
 */
class CustomizeMessageItemProvider : IContainerItemProvider.MessageProvider<CustomizeMessage>() {



    override fun bindView(p0: View?, p1: Int, p2: CustomizeMessage?, p3: UIMessage?) {

    }

    override fun onItemClick(p0: View?, p1: Int, p2: CustomizeMessage?, p3: UIMessage?) {
    }


    internal inner class ViewHolder {
        var message: ConstraintLayout? = null
    }

    override fun newView(context: Context, group: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_custom_message, null)
        val holder = ViewHolder()
        holder.message = view.findViewById(R.id.cl_main) as ConstraintLayout
        view.tag = holder
        return view
    }

    fun bindView(v: View, position: Int, content: CustomizeMessage, message: Message) {
        val holder = v.tag as ViewHolder

        if (message.getMessageDirection() === Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.message!!.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right)
        } else {
            holder.message!!.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left)
        }
//        holder.message!!.setText(content.describeContents())
//        AndroidEmoji.ensure(holder.message!!.getText() as Spannable)//显示消息中的 Emoji 表情。
    }

    override fun getContentSummary(data: CustomizeMessage): Spannable {
        return SpannableString("这是一条自定义消息CustomizeMessage")
    }

    fun onItemClick(view: View, position: Int, content: CustomizeMessage, message: Message) {

    }

    fun onItemLongClick(view: View, position: Int, content: CustomizeMessage, message: Message) {

    }

}