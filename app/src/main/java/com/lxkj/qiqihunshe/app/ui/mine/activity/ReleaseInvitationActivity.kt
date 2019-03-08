package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.text.TextUtils
import android.view.View
import android.widget.RadioGroup
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseInvitationViewModel
import com.lxkj.qiqihunshe.app.util.ProgressDialogUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.app.util.abLog
import com.lxkj.qiqihunshe.databinding.ActivityReleaseInvitationBinding
import kotlinx.android.synthetic.main.activity_release_invitation.*

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseInvitationActivity :
    BaseActivity<ActivityReleaseInvitationBinding, ReleaseInvitationViewModel>(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener, UpLoadFileCallBack {


    private var flag = -1//0吃饭，1旅行，2运动，3电影，4其他

    private var type = -1//0普通邀约，1情感邀约

    override fun getBaseViewModel() = ReleaseInvitationViewModel()

    override fun getLayoutId() = R.layout.activity_release_invitation


    private val upFileUtil by lazy { UpFileUtil(this, this) }

    override fun init() {
        initTitle("发布邀约活动")
        flag = intent.getIntExtra("flag", -1)
        type = intent.getIntExtra("type", -1)
        viewModel?.let {
            binding.viewmodel = it
            binding.model = it.model
            it.bind = binding
            it.model.typeId = flag.toString()
            it.model.category = type.toString()

            it.initViewModel()
        }

        radio.setOnCheckedChangeListener(this)
        radio_sex.setOnCheckedChangeListener(this)
        radio_fee.setOnCheckedChangeListener(this)

        tv_time.setOnClickListener(this)
        tv_adress.setOnClickListener(this)
        tv_send.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_time -> {
                viewModel?.showDate()
            }
            R.id.tv_adress -> {
                ToastUtil.showTopSnackBar(this, "地址")
            }
            R.id.tv_send -> {
                viewModel?.let {
                    it.model.notif()
                    if (TextUtils.isEmpty(it.model.title)) {
                        ToastUtil.showTopSnackBar(this, "请输入邀约标题")
                        return
                    }

                    if (TextUtils.isEmpty(it.model.content)) {
                        ToastUtil.showTopSnackBar(this, "请输入邀约内容")
                        return
                    }

                    if (TextUtils.isEmpty(it.model.status)) {
                        ToastUtil.showTopSnackBar(this, "请选择隐私状态")
                        return
                    }

                    if (TextUtils.isEmpty(it.model.starttime)) {
                        ToastUtil.showTopSnackBar(this, "请选择开始时间")
                        return
                    }
                    it.model.address = "楼下"
                    it.model.lat = "125.485489"
                    it.model.lon = "35.4548"
                    if (TextUtils.isEmpty(it.model.address)) {
                        ToastUtil.showTopSnackBar(this, "请选择活动地点")
                        return
                    }

                    if (TextUtils.isEmpty(it.model.condition)) {
                        ToastUtil.showTopSnackBar(this, "请选输入限制范围")
                        return
                    }
                    if (TextUtils.isEmpty(it.model.sex)) {
                        ToastUtil.showTopSnackBar(this, "请选选择限制性别")
                        return
                    }
                    if (TextUtils.isEmpty(it.model.fee)) {
                        ToastUtil.showTopSnackBar(this, "请选选择费用")
                        return
                    }


                    if (it.ablumList.size > 1) {
                        upFile()
                    } else {
                        viewModel!!.send().bindLifeCycle(this)
                            .subscribe({}, { toastFailure(it) })
                    }
                }
            }
        }
    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (group?.id == R.id.radio) {
            if (checkedId == R.id.rb_gk) {
                viewModel!!.model.status = "0"
            } else {
                viewModel!!.model.status = "1"
            }
        } else if (group?.id == R.id.radio_sex) {
            if (checkedId == R.id.rb_boy) {
                viewModel!!.model.sex = "1"
            } else if (checkedId == R.id.rb_girl) {
                viewModel!!.model.sex = "0"
            } else {
                viewModel!!.model.sex = "2"
            }
        } else if (group?.id == R.id.radio_fee) {
            if (checkedId == R.id.rb_aa) {
                viewModel!!.model.fee = "0"
            } else if (checkedId == R.id.rb_you) {
                viewModel!!.model.fee = "1"
            } else {
                viewModel!!.model.fee = "2"
            }
        }
    }


    fun upFile() {
        ProgressDialogUtil.showProgressDialog(this)
        val list = ArrayList<String>()
        for (i in 0 until viewModel!!.ablumList.size - 1) {//最后一个占位图
            list.add(viewModel!!.ablumList[i].path)
        }

        upFileUtil.setListPath(list)
    }


    //上传图片成功
    override fun uoLoad(url: List<String>) {
        abLog.e("上传图片成功", Gson().toJson(url))
        ProgressDialogUtil.dismissDialog()
        val sb = StringBuffer()
        for (i in 0 until url.size) {
            sb.append(url[i] + "|")
        }
        viewModel!!.model.image = sb.toString().substring(0, sb.toString().length - 1)

        viewModel!!.send().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }

    override fun uoLoad(url: String) {

    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 9, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
            if (PictureSelector.obtainMultipleResult(data).isNotEmpty()) {
                viewModel?.setImage(PictureSelector.obtainMultipleResult(data))
            }
        }
    }


}