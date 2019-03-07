package com.lxkj.qiqihunshe.app.ui.mine.viewmodel

import android.content.Intent
import android.databinding.ObservableField
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.GetTagUtil

/**
 * Created by Slingge on 2019/3/2
 */
class QuestionsAuthenViewModel : BaseViewModel() {

    val questions = ObservableField<String>()

    private var count = 0
    private val questionsList by lazy { ArrayList<String>() }


    fun getQuestions() {
        if (questionsList.isEmpty()) {
            GetTagUtil(activity!!, object : GetTagUtil.TagListCallback {
                override fun TagList(tagList: ArrayList<String>) {
                    questionsList.addAll(tagList)
                    if (questionsList.isEmpty()) {
                        return
                    }
                    next()
                }
            }).getTag("0", "9")
        }

    }

    fun next() {
        if (count >= questionsList.size) {
            val intent = Intent()
            intent.putExtra("fea", "feawf")
            activity?.setResult(0, intent)
            activity?.finish()
            return
        }
        questions.set(questionsList[count])
        count++
    }


}