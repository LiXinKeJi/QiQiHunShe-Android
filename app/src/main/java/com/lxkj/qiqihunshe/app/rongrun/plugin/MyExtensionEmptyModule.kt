package com.lxkj.qiqihunshe.app.rongrun.plugin

import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.plugin.IPluginModule
import io.rong.imlib.model.Conversation

/**
 * Created by Slingge on 2018/12/26
 */
class MyExtensionEmptyModule : DefaultExtensionModule() {


    override fun getPluginModules(conversationType: Conversation.ConversationType): List<IPluginModule> {
        val pluginModules = super.getPluginModules(conversationType)
        pluginModules.clear()
        return pluginModules
    }

}
