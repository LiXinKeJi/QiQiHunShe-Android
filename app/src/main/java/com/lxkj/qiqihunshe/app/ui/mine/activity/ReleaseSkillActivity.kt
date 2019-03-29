package com.lxkj.qiqihunshe.app.ui.mine.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.baidu.mapapi.search.core.PoiInfo
import com.luck.picture.lib.PictureSelector
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.map.activity.ChooseAddressActivity
import com.lxkj.qiqihunshe.app.ui.mine.viewmodel.ReleaseSkillViewModel
import com.lxkj.qiqihunshe.app.util.FileUtil
import com.lxkj.qiqihunshe.app.util.ImageUtil
import com.lxkj.qiqihunshe.app.util.SelectPictureUtil
import com.lxkj.qiqihunshe.app.util.ToastUtil
import com.lxkj.qiqihunshe.databinding.ActivityReleaseSkillBinding
import kotlinx.android.synthetic.main.activity_release_skill.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Slingge on 2019/2/25
 */
class ReleaseSkillActivity : BaseActivity<ActivityReleaseSkillBinding, ReleaseSkillViewModel>(),
    View.OnClickListener {


    override fun getBaseViewModel() = ReleaseSkillViewModel()

    override fun getLayoutId() = R.layout.activity_release_skill


    override fun init() {
        initTitle("发布才艺")

        iv_video.setOnClickListener(this)

        viewModel?.let {
            binding.model = it.model
            binding.viewmodel = it
            it.bind = binding
            it.init()
        }
        tv_address.setOnClickListener(this)
        tv_send.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_video -> {
                SelectPictureUtil.selectVodeoPicture(this, 1, 0)
            }
            R.id.tv_address -> {
                MyApplication.openActivityForResult(this, ChooseAddressActivity::class.java, 1)
            }
            R.id.tv_send -> {
                viewModel?.let {
                    if (TextUtils.isEmpty(it.title.get())) {
                        ToastUtil.showTopSnackBar(this, "请输入标题")
                        return
                    }
                    it.model.title = it.title.get()!!
                    if (TextUtils.isEmpty(it.content.get())) {
                        ToastUtil.showTopSnackBar(this, "请输入内容")
                        return
                    }
                    it.model.content = it.content.get()!!


                    if (TextUtils.isEmpty(it.model.video)) {
                        ToastUtil.showTopSnackBar(this, "请选择/拍摄视频内容")
                        return
                    }
                    if (TextUtils.isEmpty(it.model.location)) {
                        ToastUtil.showTopSnackBar(this, "请选择当前位置")
                        return
                    }
                    it.releaseSkill().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
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
            SelectPictureUtil.selectPicture(this, 9, 0, false)
        } else {//禁止使用权限，询问是否设置允许
            PermissionsDialog.dialog(this, "需要访问内存卡和拍照权限")
        }
    }


    private var PreviewingBitmap: Bitmap? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        if (requestCode == 0) {
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
                    viewModel?.upVideo(videoPath)
                    Thread(Runnable {
                        PreviewingBitmap = ImageUtil.getVideoThumbnail(this, videoPath)
                        handler.sendEmptyMessage(1)
                    }).start()
                }
            }
        }
        if (requestCode == 1) {
            var poi = data.getParcelableExtra("poi") as PoiInfo
            if (null != poi) {
                binding.model?.lat = poi.location.latitude.toString()
                binding.model?.lon = poi.location.latitude.toString()
                binding.model?.location = poi.name
                tv_address.text = poi.name
            }
        }
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> if (PreviewingBitmap != null) {
                    iv_video.scaleType = ImageView.ScaleType.CENTER_CROP
                    FileUtil.saveFile(this@ReleaseSkillActivity, "image.jpg", PreviewingBitmap)
                    iv_video.setImageBitmap(PreviewingBitmap)
                    val dateFolder = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
                        .format(Date())
                    viewModel?.upVideo(Environment.getExternalStorageDirectory().path + "/yitongcheng/" + dateFolder + "/image.jpg")
                }
            }
        }
    }

}