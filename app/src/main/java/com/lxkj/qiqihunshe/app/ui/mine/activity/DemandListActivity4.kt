package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.DemandListViewModel4
import com.lxkj.qiqihunshe.app.util.AbStrUtil
import com.lxkj.qiqihunshe.app.util.StatusBarBlackWordUtil
import com.lxkj.qiqihunshe.app.util.StatusBarUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityDemandList4Binding
import kotlinx.android.synthetic.main.activity_demand_list4.*
import kotlinx.android.synthetic.main.dialog_report1.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * 4定制推荐 5牵引安排
 * Created by Slingge on 2019/3/28
 */
class DemandListActivity4 :
    BaseActivity<ActivityDemandList4Binding, DemandListViewModel4>(), View.OnClickListener {


    override fun getBaseViewModel() = DemandListViewModel4()

    override fun getLayoutId() = R.layout.activity_demand_list4

    override fun init() {
        this.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        initTitle("填写需求列表")

        rl_include.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setBackgroundColor(resources.getColor(R.color.colorTheme))
        tv_title.setTextColor(resources.getColor(R.color.white))
        AbStrUtil.setDrawableLeft(this, R.drawable.ic_back_w, tv_title, 10)

        isWhiteStatusBar = false
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorTheme))
            StatusBarBlackWordUtil.StatusBarLightMode(this)
        }

        scroll.scrollTo(0, 0)
        viewModel?.let {
            it.bind = binding
            binding.viewmodel = it
            it.init()
            it.getXuqiu().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
        }

        rl_he_hometown.setOnClickListener(this)
        rl_he_residence.setOnClickListener(this)
        rl_he_car.setOnClickListener(this)
        rl_he_room.setOnClickListener(this)
        rl_he_education.setOnClickListener(this)

        tv_pay.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_he_car -> {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("是否有车？")
                dialog.setPositiveButton(
                    "有"
                ) { arg0, arg1 ->
                    //车 0无 1有
                    viewModel?.let {
                        it.model.car = "1"
                        tv_he_car.text = "有"
                    }
                    arg0.dismiss()
                }
                dialog.setNegativeButton("无") { arg0, arg1 ->
                    viewModel?.let {
                        it.model.car = "0"
                        tv_he_car.text = "无"
                    }
                    arg0.dismiss()
                }
                dialog.show()
            }
            R.id.rl_he_room -> {
                val dialog = AlertDialog.Builder(this)
                dialog.setMessage("是否有房？")
                dialog.setPositiveButton(
                    "有"
                ) { arg0, arg1 ->
                    ////车 0无 1有
                    viewModel?.let {
                        it.model.house = "1"
                        tv_he_room.text = "有"
                    }
                    arg0.dismiss()
                }
                dialog.setNegativeButton("无") { arg0, arg1 ->
                    viewModel?.let {
                        it.model.house = "0"
                        tv_he_room.text = "无"
                    }
                    arg0.dismiss()
                }
                dialog.show()
            }
            R.id.rl_he_education -> {
                viewModel?.getEdu()
            }
            R.id.rl_he_hometown -> {
                viewModel?.let {
                    it.showAddress(0)
                }
            }
            R.id.rl_he_residence -> {
                viewModel?.let {
                    it.showAddress(1)
                }
            }
            R.id.tv_pay -> {
                viewModel?.model?.let {
                    if (TextUtils.isEmpty(it.birthplace) || AbStrUtil.tvTostr(tv_he_hometown) == "请选择") {
                        ToastUtil.showTopSnackBar(this, "请选择家乡")
                        return
                    }
                    if (TextUtils.isEmpty(it.residence) || AbStrUtil.tvTostr(tv_he_residence) == "请选择") {
                        ToastUtil.showTopSnackBar(this, "请选择现居")
                        return
                    }
                    it.noti()
                    if (TextUtils.isEmpty(it.age)) {
                        ToastUtil.showTopSnackBar(this, "请输入年龄")
                        return
                    }
                    if (TextUtils.isEmpty(it.salary)) {
                        ToastUtil.showTopSnackBar(this, "请输入收入")
                        return
                    }

                    if (TextUtils.isEmpty(it.education) || AbStrUtil.tvTostr(tv_he_education) == "请选择") {
                        ToastUtil.showTopSnackBar(this, "请选择学历")
                        return
                    }

                    val content = AbStrUtil.etTostr(et_content)
                    it.content = content

                }
                viewModel!!.BuyPer().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
        }
    }


}