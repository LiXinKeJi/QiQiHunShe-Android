package com.lxkj.qiqihunshe.app.rongrun

import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.plugin.IPluginModule
import io.rong.imlib.model.Conversation

/**
 * Created by Slingge on 2018/12/26
 */
class MyExtensionModule : DefaultExtensionModule() {
    override fun getPluginModules(conversationType: Conversation.ConversationType): List<IPluginModule> {
        val pluginModules = super.getPluginModules(conversationType)
//        pluginModules.clear()
//        pluginModules.add(ImagePlugin())
//        pluginModules.add(FilePlugin())
        pluginModules.add(LocationPlugin())
        return pluginModules
    }

}
