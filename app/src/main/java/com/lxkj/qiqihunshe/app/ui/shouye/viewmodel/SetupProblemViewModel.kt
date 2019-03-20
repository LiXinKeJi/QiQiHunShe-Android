package com.lxkj.qiqihunshe.app.ui.shouye.viewmodel

import android.databinding.ObservableField
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseViewModel
import com.lxkj.qiqihunshe.app.retrofitnet.SingleCompose
import com.lxkj.qiqihunshe.app.retrofitnet.SingleObserverInterface
import com.lxkj.qiqihunshe.app.retrofitnet.async
import com.lxkj.qiqihunshe.app.ui.shouye.adapter.CheckBoxAdapter
import com.lxkj.qiqihunshe.app.ui.shouye.model.SetupProblemModel
import com.lxkj.qiqihunshe.app.ui.shouye.model.SubmissionAnswerModel
import com.lxkj.qiqihunshe.app.util.DisplayUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivitySetupProblemBinding
import io.reactivex.Single

/**
 * Created by Slingge on 2019/2/26
 */
class SetupProblemViewModel : BaseViewModel() {

    var bind: ActivitySetupProblemBinding? = null

    private var count = -1

    var Problem = ObservableField<String>()
    val ids by lazy { ArrayList<String>() } ////答案id

    val checkAdapter by lazy { CheckBoxAdapter() }

    private val dataList by lazy { ArrayList<SetupProblemModel.dataModel>() }
    private val answerList by lazy { ArrayList<SetupProblemModel.answerModel>() }
    fun init() {
        bind?.let {
            it.recycler.layoutManager = LinearLayoutManager(activity)
            it.recycler.adapter = checkAdapter
        }

    }

    /**
     * 获取所有问题
     */
    fun getQuestion(): Single<String> {
        val json = "{\"cmd\":\"getQuestion\",\"uid\":\"" + StaticUtil.uid + "\"}"
        return retrofit.getData(json)
            .async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    val model = Gson().fromJson(response, SetupProblemModel::class.java)
                    dataList.addAll(model.dataList)
                    next()
                }
            }, activity))
    }


    fun next() {
        count++
        if (count >= dataList.size) {
            ToastUtil.showToast("问题设置完成")
            activity?.finish()
        }

        bind?.let {
            it.pbReputation.progress = count
            it.tvPress.text = "$count/${dataList.size}"
        }

        Problem.set(dataList[count].title)
        answerList.clear()
        answerList.addAll(dataList[count].answerList)

        checkAdapter.flag = 1
        checkAdapter.upData(answerList)
    }


    /**
     * 提交答案
     */
    fun Submission(): Single<String> {
        abLog.e(
            "提交答案", Gson().toJson(SubmissionAnswerModel(ids))
        )
        return retrofit.getData(Gson().toJson(SubmissionAnswerModel(ids))).async()
            .compose(SingleCompose.compose(object : SingleObserverInterface {
                override fun onSuccess(response: String) {
                    ids.clear()
                    next()
                }
            }, activity))
    }


    fun setId(isSelect: Boolean, position: Int) {
        if (isSelect) {
            answerList[position].isSelect = true
            ids.add(answerList[position].aid)
        } else {
            answerList[position].isSelect = false
            ids.remove(answerList[position].aid)

        }
    }


}