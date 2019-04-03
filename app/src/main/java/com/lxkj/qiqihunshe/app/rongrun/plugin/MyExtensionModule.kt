package com.lxkj.qiqihunshe.app.rongrun.plugin

import com.google.gson.Gson
import com.lxkj.qiqihunshe.app.rongrun.RongYunUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.plugin.IPluginModule
import io.rong.imkit.widget.provider.FilePlugin
import io.rong.imlib.model.Conversation

/**
 * Created by Slingge on 2018/12/26
 */
class MyExtensionModule(val flag: Int) : DefaultExtensionModule() {


    override fun getPluginModules(conversationType: Conversation.ConversationType): List<IPluginModule> {
        val pluginModules = super.getPluginModules(conversationType)
        abLog.e("插件", Gson().toJson(pluginModules))
//        pluginModules.add(FilePlugin())
//        pluginModules.add(ImagePlugin())
        pluginModules.add(LocationPlugin())
        RongYunUtil.PluginList.clear()
        if (RongYunUtil.PluginList.isEmpty()) {
            RongYunUtil.PluginList.addAll(pluginModules)
        }
        if (flag == 0) {
            pluginModules.clear()
            return pluginModules
        } else {
            return RongYunUtil.PluginList
        }
    }

}
