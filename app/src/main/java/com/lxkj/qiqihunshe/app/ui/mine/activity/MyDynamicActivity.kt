package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.google.gson.Gson
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangAfterDialog
import com.lxkj.qiqihunshe.app.ui.dialog.DaShangDialog
import com.lxkj.qiqihunshe.app.ui.dialog.ReportDialog1
import com.lxkj.qiqihunshe.app.ui.mine.model.SpaceDynamicModel
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.MyDynamicViewModel
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.ShareUtil
import com.lxkj.qiqihunshe.app.util.StaticUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityMydynamicBinding
import io.rong.push.platform.hms.common.StrUtils
import kotlinx.android.synthetic.main.activity_mydynamic.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.include_v.*
import zhanghuan.cn.emojiconlibrary.FaceConversionUtil
import zhanghuan.cn.emojiconlibrary.FaceRelativeLayout


/**
 * 我的动态、动态详情
 * Created by Slingge on 2019/2/25
 */
class MyDynamicActivity : BaseActivity<ActivityMydynamicBinding, MyDynamicViewModel>(), View.OnClickListener {

    private var flag = -1//0我的动态，1动态详情

    private var position = -1//所在列表下标

    override fun getBaseViewModel() = MyDynamicViewModel()

    override fun getLayoutId() = R.layout.activity_mydynamic

    private lateinit var et_sendmessage: EditText

    override fun init() {

        et_sendmessage = findViewById(R.id.et_sendmessage)

        flag = intent.getIntExtra("flag", -1)

        var id = ""
        if (TextUtils.isEmpty(intent.getStringExtra("id"))) {
            val model = intent.getSerializableExtra("bean") as SpaceDynamicModel.dataModel
            id = model.userId
        } else {
            id = intent.getStringExtra("id")
        }

        position = intent.getIntExtra("position", -1)

        if (id == StaticUtil.uid) {
            cl_person.visibility = View.GONE
            tv_reward.visibility = View.INVISIBLE

            tv_right.visibility = View.VISIBLE
            tv_right.text = "删除"
            tv_right.setOnClickListener(this)
            initTitle("我的动态")
        } else {
            initTitle("动态详情")
            cl_person.visibility = View.VISIBLE
            tv_reward.visibility = View.VISIBLE
            tv_reward.setOnClickListener(this)

            ReportDialog1.diss()
            tv_report.visibility = View.VISIBLE
            tv_report.setOnClickListener(this)
        }

        viewModel?.let {

            binding.viewmodel = it
            it.bind = binding

            it.initViewModel()

            if (intent.getSerializableExtra("bean") != null) {
                it.model = intent.getSerializableExtra("bean") as SpaceDynamicModel.dataModel
                binding.model = it.model
                setData(it.model)
            } else {
                it.getDynmic(id).bindLifeCycle(this).subscribe({
                    abLog.e("动态详情",it)
                    viewModel!!.model = Gson().fromJson(it, SpaceDynamicModel.dataModel::class.java)
                    viewModel!!.model.dongtaiId=id
                    binding.model = viewModel!!.model
                    setData(viewModel!!.model)
                }, {})
            }

            it.adapter.setLoadMore {
                it.page++
                if (it.page <= it.totalPage) {
                    it.getComment().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
                }
            }
        }

        header.setOnClickListener(this)
        findViewById<ImageView>(R.id.btn_send).setOnClickListener(this)
        tv_zan.setOnClickListener(this)
        tv_share.setOnClickListener(this)
        iv_emoj.setOnClickListener(this)
    }


    fun setData(model: SpaceDynamicModel.dataModel) {
        viewModel!!.getComment().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        viewModel!!.imageAdapter.loadMore(model.images, 1)

        if (model.zan == "0") {
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_nor, tv_zan, 5)
        } else {
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_zan_hl, tv_zan, 5)
        }

        if (model.sex == "0") {//0女 1男
            tv_age.setBackgroundResource(R.drawable.bg_girl)
            tv_age.setTextColor(resources.getColor(R.color.girl))
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_girl, tv_age, 3)
        } else {
            tv_age.setBackgroundResource(R.drawable.thems_bg35)
            tv_age.setTextColor(resources.getColor(R.color.colorThemes))
            AbStrUtil.setDrawableLeft(this, R.drawable.ic_boy, tv_age, 3)
        }

        if (model.identity == "1") {//1单身 2约会 3牵手
            iv_state.setImageResource(R.drawable.danshen)
        } else if (model.identity == "2") {
            iv_state.setImageResource(R.drawable.yuehui)
        } else {
            iv_state.setImageResource(R.drawable.qianshou)
        }

        for (i in 0 until model.permission.size) {
            selectLv(model.permission[i])
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send/*, R.id.iv_send */ -> {
                val str = AbStrUtil.etTostr(et_sendmessage)
                if (TextUtils.isEmpty(str)) {
                    return
                }
                viewModel!!.sendComment(str, et_sendmessage).bindLifeCycle(this).subscribe({}, { it })
            }
            R.id.header -> {
                val bundle = Bundle()
                bundle.putString("userId", viewModel!!.model.userId)
                MyApplication.openActivity(this, PersonalInfoActivity::class.java, bundle)
            }
            R.id.tv_zan -> {
                viewModel!!.zan().bindLifeCycle(this).subscribe({}, { it })
            }
            R.id.tv_share -> {
                ShareUtil.share(this)
            }
            R.id.tv_report -> {//举报
                ReportDialog1.getReportList(this, "2", object : ReportDialog1.ReportCallBack {
                    override fun report(report: String) {
                        viewModel!!.jubao(report).bindLifeCycle(this@MyDynamicActivity)
                            .subscribe({}, { toastFailure(it) })
                    }
                })
            }
            R.id.tv_reward -> {
                DaShangDialog.show(this, object : DaShangDialog.DaShangCallBack {
                    override fun dashang(money: String) {
                        viewModel!!.dashang(money).bindLifeCycle(this@MyDynamicActivity)
                            .subscribe({}, { toastFailure(it) })
                    }
                })
            }
            R.id.tv_right -> {
                viewModel!!.DelDynamuc(position).bindLifeCycle(this).subscribe({}, { it })
            }
        }
    }


    fun selectLv(state: String) {
        when (state) {
            "1" -> iv_v1.visibility = View.VISIBLE
            "2" -> iv_v2.visibility = View.VISIBLE
            "3" -> iv_v3.visibility = View.VISIBLE
            "4" -> iv_v4.visibility = View.VISIBLE
            "5" -> iv_v5.visibility = View.VISIBLE
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 202 && resultCode == 303) {
            DaShangAfterDialog.show(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ReportDialog1.diss()
        DaShangDialog.diss()
        DaShangAfterDialog.diss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && (findViewById<FaceRelativeLayout>(R.id.FaceRelativeLayout))
                .hideFaceView()
        ) {
            true
        } else super.onKeyDown(keyCode, event)
    }


}