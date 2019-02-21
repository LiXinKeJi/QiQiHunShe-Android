package com.lxkj.qiqihunshe.app.ui.mine.adapter

import android.content.Context
import com.lxkj.qiqihunshe.app.base.BaseListAdapter
import com.lxkj.qiqihunshe.app.ui.mine.model.PersonaSkillModel
import com.lxkj.qiqihunshe.app.ui.mine.widget.PersonSkillItemView

/**
 * Created by Slingge on 2019/2/21
 */
class PersonSkillAdapter : BaseListAdapter<PersonaSkillModel, PersonSkillItemView>() {


    override fun getitemView(context: Context): PersonSkillItemView {
        return PersonSkillItemView(context)
    }

    override fun refreshItemView(view: PersonSkillItemView, itembean: PersonaSkillModel) {
    }


}