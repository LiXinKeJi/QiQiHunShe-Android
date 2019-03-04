package com.lxkj.qiqihunshe.app.ui.entrance

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.RadioGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.ui.dialog.AddressPop
import com.lxkj.qiqihunshe.app.ui.dialog.DatePop
import com.lxkj.qiqihunshe.app.util.UpFileUtil
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.entrance.model.PerfectInfoModel
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.PerfectInfoViewModel
import com.lxkj.qiqihunshe.app.ui.model.CityModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityPerfectInfoBinding
import kotlinx.android.synthetic.main.activity_perfect_info.*
import kotlinx.android.synthetic.main.include_title.*
import java.io.File


/**
 * Created by Slingge on 2019/2/19
 */
class PerfectInfoActivitiy :
    BaseActivity<ActivityPerfectInfoBinding, PerfectInfoViewModel>(), View.OnClickListener, UpLoadFileCallBack
    , RadioGroup.OnCheckedChangeListener {


    override fun getBaseViewModel() = PerfectInfoViewModel()

    override fun getLayoutId() = R.layout.activity_perfect_info

    private val ImageList by lazy { ArrayList<LocalMedia>() }

    private val upload by lazy { UpFileUtil(this, this) }


    override fun init() {
        initTitle("完善资料")

        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"
        tv_right.setOnClickListener(this)

        tv_agree.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
        tv_agree.paint.isAntiAlias = true//抗锯齿

        tv_agree.setOnClickListener(this)
        rl_header.setOnClickListener(this)
        rl_mytype.setOnClickListener(this)

        viewModel?.let {
            it.bind = binding
            binding.viewmodel = it
            binding.model = it.model
        }

        rg_sex.setOnCheckedChangeListener(this)
        rl_birthday.setOnClickListener(this)
        rl_hometown.setOnClickListener(this)
        rl_residence.setOnClickListener(this)
        tv_nation.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_birthday -> {
                viewModel?.showDate(0)
            }
            R.id.rl_hometown -> {
                viewModel?.showAddress(0)
            }
            R.id.rl_residence -> {
                viewModel?.showAddress(1)
            }
            R.id.tv_nation -> {//民族

            }
            R.id.tv_agree -> {

            }
            R.id.rl_header -> {
                ImageList.clear()
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
                    SelectPictureUtil.selectPicture(this, 10, 0, false)
                }
            }
            R.id.rl_mytype -> {
                viewModel?.model?.noti()
                MyApplication.openActivity(this, MyTypeActivity::class.java)
            }
            R.id.tv_right -> {
                viewModel?.model?.noti()
                if (ImageList.isEmpty()) {
                    ToastUtil.showTopSnackBar(this, "请选择头像")
                    return
                }


                if (TextUtils.isEmpty(viewModel?.model?.nickname)) {
                    ToastUtil.showTopSnackBar(this, "请输入昵称")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.introduction)) {
                    ToastUtil.showTopSnackBar(this, "请输入个人签名")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.realname)) {
                    ToastUtil.showTopSnackBar(this, "请输入真实姓名")
                    return
                }

                if (TextUtils.isEmpty(viewModel?.model?.marriage)) {
                    ToastUtil.showTopSnackBar(this, "请选择性别")
                    return
                }

                if (TextUtils.isEmpty(viewModel?.model?.birthday)) {
                    ToastUtil.showTopSnackBar(this, "请选择出生日期")
                    return
                }

                if (TextUtils.isEmpty(viewModel?.model?.height)) {
                    ToastUtil.showTopSnackBar(this, "请输入身高")
                    return
                }

                if (TextUtils.isEmpty(viewModel?.model?.birthplace)) {
                    ToastUtil.showTopSnackBar(this, "请选择家乡")
                    return
                }


                ProgressDialogUtil.showProgressDialog(this)
                val pathList = ArrayList<String>()
                for (i in 0 until ImageList.size) {
                    pathList.add(ImageList[i].path)
                }
                upload.setListPath(pathList)


            }
        }
    }


    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (group?.id) {
            R.id.rb_boy -> {
                viewModel?.model?.marriage = "1"
            }
            R.id.rb_girl -> {
                viewModel?.model?.marriage = "0"
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectPicture(this, 10, 0, true)
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
                for (model: LocalMedia in PictureSelector.obtainMultipleResult(data)) {
                    ImageList.add(ImageList.size, model)
                }
                val file = File(ImageList[0].compressPath)
                //加载图片
                Glide.with(this).load(file).into(iv_header)
            }
        }
    }

    override fun uoLoad(url: String) {

    }

    override fun uoLoad(url: List<String>) {
        ProgressDialogUtil.dismissDialog()
        abLog.e("上传图片完成", Gson().toJson(url))


    }


}