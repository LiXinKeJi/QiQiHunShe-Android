package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.RealNameAuthenViewModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityRealnameAuthenBinding
import kotlinx.android.synthetic.main.activity_realname_authen.*
import kotlinx.android.synthetic.main.include_title.*

/**
 * Created by Slingge on 2019/2/20
 */
class RealNameAuthenActivity : BaseActivity<ActivityRealnameAuthenBinding, RealNameAuthenViewModel>(),
    View.OnClickListener {
    override fun getBaseViewModel() = RealNameAuthenViewModel()

    override fun getLayoutId() = R.layout.activity_realname_authen

    override fun init() {
        initTitle("实名认证")
        tv_right.visibility = View.VISIBLE
        tv_right.text = "保存"

        tv_right.setOnClickListener(this)
        iv_video.setOnClickListener(this)
        fl_Id.setOnClickListener(this)
        fl_edu.setOnClickListener(this)
        fl_room.setOnClickListener(this)
        fl_car.setOnClickListener(this)
        fl_salary.setOnClickListener(this)
        fl_answer.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_right -> {
                viewModel!!.model.let {
                    if (TextUtils.isEmpty(it.video)) {
                        ToastUtil.showTopSnackBar(this, "请选择3秒短视频")
                        return
                    }
                    if (TextUtils.isEmpty(it.idnumber)) {
                        ToastUtil.showTopSnackBar(this, "请选择身份证照")
                        return
                    }
                    if (TextUtils.isEmpty(it.car)) {
                        ToastUtil.showTopSnackBar(this, "请选择驾驶证照")
                        return
                    }
                    if (TextUtils.isEmpty(it.house)) {
                        ToastUtil.showTopSnackBar(this, "请选择房产证照")
                        return
                    }
                    if (TextUtils.isEmpty(it.salary)) {
                        ToastUtil.showTopSnackBar(this, "请选择工作证照")
                        return
                    }
                    if (TextUtils.isEmpty(it.education)) {
                        ToastUtil.showTopSnackBar(this, "请选择学历证书")
                        return
                    }
                    if (tv6.text.toString() != "已认证") {
                        ToastUtil.showTopSnackBar(this, "请回答认证")
                        return
                    }
                }
                viewModel!!.UpData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }
            R.id.iv_video -> {
                SelectPictureUtil.selectVodeo3(this, 1, 6)
            }
            R.id.fl_Id -> {
                val bundle = Bundle()
                bundle.putInt("flag", 4)
                MyApplication.openActivityForResult(this, UploadCertificatesActivity::class.java, bundle, 4)
            }
            R.id.fl_edu -> {
                val bundle = Bundle()
                bundle.putInt("flag", 0)
                MyApplication.openActivityForResult(this, UploadCertificatesActivity::class.java, bundle, 0)
            }
            R.id.fl_room -> {
                val bundle = Bundle()
                bundle.putInt("flag", 1)
                MyApplication.openActivityForResult(this, UploadCertificatesActivity::class.java, bundle, 1)
            }
            R.id.fl_car -> {
                val bundle = Bundle()
                bundle.putInt("flag", 2)
                MyApplication.openActivityForResult(this, UploadCertificatesActivity::class.java, bundle, 2)
            }
            R.id.fl_salary -> {
                val bundle = Bundle()
                bundle.putInt("flag", 3)
                MyApplication.openActivityForResult(this, UploadCertificatesActivity::class.java, bundle, 3)
            }
            R.id.fl_answer -> {
                MyApplication.openActivityForResult(this, QuestionsAuthenActivity::class.java, 5)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        when (requestCode) {
            0 -> {//学历
                viewModel!!.model.education = data.getStringExtra("lable")
                viewModel!!.model.diploma = data.getStringExtra("url")
                tv2.text = "已选择"
            }
            1 -> {//房子
                viewModel!!.model.house = data.getStringExtra("lable")
                viewModel!!.model.houseimage = data.getStringExtra("url")
                tv3.text = "已选择"
            }
            2 -> {//车
                viewModel!!.model.car = data.getStringExtra("lable")
                viewModel!!.model.carimage = data.getStringExtra("url")
                tv4.text = "已选择"
            }
            3 -> {//薪资
                viewModel!!.model.salary = data.getStringExtra("lable")
                viewModel!!.model.workcard = data.getStringExtra("url")
                tv5.text = "已选择"
            }
            4 -> {//身份证
                viewModel!!.model.idnumber = data.getStringExtra("lable")
                viewModel!!.model.idcard1 = data.getStringExtra("url")
                viewModel!!.model.idcard2 = data.getStringExtra("url2")
                tv1.text = "已选择"
            }
            5 -> {//问答
                viewModel!!.Questions = true
                tv6.text = "已认证"
            }
            6 -> {
                if (PictureSelector.obtainMultipleResult(data).isNotEmpty()) {
                    val videoPath = PictureSelector.obtainMultipleResult(data)[0].path
                    if (videoPath.endsWith(".mp4") || videoPath.endsWith(".avi") || videoPath.endsWith(".mkv")
                        || videoPath.endsWith(".FLV") || videoPath.endsWith(".MPG") || videoPath.endsWith(
                            ".RMVB"
                        )
                        || videoPath.endsWith(".3gp") || videoPath.endsWith(".WMV")
                        || videoPath.endsWith(".MP4") || videoPath.endsWith(".AVI") || videoPath.endsWith(
                            ".MKV"
                        )
                        || videoPath.endsWith(".flv") || videoPath.endsWith(".mpg") || videoPath.endsWith(
                            ".rmvb"
                        )
                        || videoPath.endsWith(".3GP") || videoPath.endsWith(".wmv")
                    ) {
                        Thread(Runnable {
                            PreviewingBitmap = BitmapUtil.decode(ImageUtil.getVideoThumbnail2(videoPath), 100, 100)
                            handler.sendEmptyMessage(1)
                        }).start()
                        ProgressDialogUtil.showProgressDialog(this)
                        viewModel?.upFile(videoPath)
                    }
                }
            }
        }
    }


    /**
     * 申请权限结果回调
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 0) {//询问结果
            SelectPictureUtil.selectVodeo3(this, 1, 6)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }


    private var PreviewingBitmap: Bitmap? = null

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 ->
                    if (PreviewingBitmap != null) {
                        iv_video.scaleType = ImageView.ScaleType.CENTER_CROP
                        iv_video.setImageBitmap(PreviewingBitmap)
                    }
            }
        }
    }


}