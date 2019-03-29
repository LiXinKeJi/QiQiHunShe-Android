package com.lxkj.qiqihunshe.app.ui.entrance

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Paint
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.RadioGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.entity.LocalMedia
import com.lxkj.qiqihunshe.R
import com.lxkj.qiqihunshe.app.MyApplication
import com.lxkj.qiqihunshe.app.base.BaseActivity
import com.lxkj.qiqihunshe.app.customview.SwitchView
import com.lxkj.qiqihunshe.app.interf.UpLoadFileCallBack
import com.lxkj.qiqihunshe.app.retrofitnet.UpFileUtil
import com.lxkj.qiqihunshe.app.retrofitnet.bindLifeCycle
import com.lxkj.qiqihunshe.app.ui.dialog.PermissionsDialog
import com.lxkj.qiqihunshe.app.ui.entrance.viewmodel.PerfectInfoViewModel
import com.lxkj.qiqihunshe.app.util.*
import com.lxkj.qiqihunshe.databinding.ActivityPerfectInfoBinding
import kotlinx.android.synthetic.main.activity_perfect_info.*
import kotlinx.android.synthetic.main.dialog_report1.*
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
        this.window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
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
            it.birthplace2.set("请选择")
            it.residence2.set("请选择")

            if (intent.getIntExtra("flag", -1) == 0) {
                it.getData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
            }

        }

        rg_sex.setOnCheckedChangeListener(this)
        rl_birthday.setOnClickListener(this)
        rl_hometown.setOnClickListener(this)
        rl_residence.setOnClickListener(this)
        tv_nation.setOnClickListener(this)
        rl_education.setOnClickListener(this)
        rl_emotional_state.setOnClickListener(this)
        rl_emotional_planning.setOnClickListener(this)
        rl_mytype.setOnClickListener(this)
        rl_hobby.setOnClickListener(this)
        rl_label.setOnClickListener(this)

        rl_he_type.setOnClickListener(this)
        rl_he_hometown.setOnClickListener(this)
        rl_he_residence.setOnClickListener(this)
        rl_he_emotional_state.setOnClickListener(this)
        rl_he_emotional_planning.setOnClickListener(this)
        rl_he_salary.setOnClickListener(this)
        rl_he_car.setOnClickListener(this)
        rl_he_room.setOnClickListener(this)
        rl_he_education.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_birthday -> {
                viewModel?.showDate(0)
            }
            R.id.rl_hometown -> {//我的家乡
                viewModel?.showAddress(0)
            }
            R.id.rl_residence -> {//我的现居
                viewModel?.showAddress(1)
            }
            R.id.tv_nation -> {//民族
                viewModel?.nation()
            }
            R.id.rl_education -> {//我的学历
                viewModel?.getEdu()
            }
            R.id.rl_emotional_state -> {//我的情感状态
                viewModel?.getEmotional()
            }
            R.id.rl_emotional_planning -> {//我的情感计划
                viewModel?.getEmotionalPlanning()
            }
            R.id.rl_mytype -> {//我的类型
                viewModel?.getMyType()
            }
            R.id.rl_hobby -> {//我的兴趣爱好
                viewModel?.getHobby()
            }
            R.id.rl_label -> {//地点标签
                viewModel?.getLabel()
            }
            R.id.rl_he_type -> {//他的类型
                viewModel?.getHeType()
            }
            R.id.rl_he_hometown -> {//他的家乡
                viewModel?.showAddress(2)
            }
            R.id.rl_he_residence -> {//他的现居
                viewModel?.showAddress(3)
            }
            R.id.rl_he_emotional_state -> {//他的情感状态
                viewModel?.getHeEmotional()
            }
            R.id.rl_he_emotional_planning -> {//他的情感计划
                viewModel?.getHeEmotionalPlanning()
            }
            R.id.rl_he_salary -> {//他的薪资范围
                viewModel?.getHeSalary()
            }
            R.id.rl_he_car -> {//他是否有车
                viewModel?.getHeCar()
            }
            R.id.rl_he_room -> {//他的房
                viewModel?.getHeRoom()
            }
            R.id.rl_he_education -> {////他的学历
                viewModel?.showHeEdu()
            }
            R.id.tv_agree -> {//服务协议

            }
            R.id.rl_header -> {
                ImageList.clear()
                if (PermissionUtil.ApplyPermissionAlbum(this, 0)) {
                    SelectPictureUtil.selectPicture(this, 10, 0, false)
                }
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

                if (TextUtils.isEmpty(viewModel?.model?.sex)) {
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
                if (TextUtils.isEmpty(viewModel?.model?.residence)) {
                    ToastUtil.showTopSnackBar(this, "请选择现居地")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.nation)) {
                    ToastUtil.showTopSnackBar(this, "请选择民族")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.job)) {
                    ToastUtil.showTopSnackBar(this, "请输入职业")
                    return
                }
               /* if (TextUtils.isEmpty(viewModel?.model?.education)) {
                    ToastUtil.showTopSnackBar(this, "请选择学历")
                    return
                }*/

                if (TextUtils.isEmpty(viewModel?.model?.marriage)) {
                    ToastUtil.showTopSnackBar(this, "请选择情感状态")
                    return
                }

                if (TextUtils.isEmpty(viewModel?.model?.plan)) {
                    ToastUtil.showTopSnackBar(this, "请选择情感计划")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.type)) {
                    ToastUtil.showTopSnackBar(this, "请选择我的类型")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.interests)) {
                    ToastUtil.showTopSnackBar(this, "请选择兴趣爱好")
                    return
                }
                if (TextUtils.isEmpty(viewModel?.model?.locales)) {
                    ToastUtil.showTopSnackBar(this, "请选择地点标签")
                    return
                }

                if (sw_mate.isOpened) {
                    if (TextUtils.isEmpty(viewModel?.model?.type2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶类型")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.birthplace2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶家乡")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.residence2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶现居")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.height2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶身高")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.marriage2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶情感状态")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.plan2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶情感计划")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.salary2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶薪资范围")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.car2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶车辆")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.house2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶情房子")
                        return
                    }
                    if (TextUtils.isEmpty(viewModel?.model?.education2)) {
                        ToastUtil.showTopSnackBar(this, "请选择择偶学历")
                        return
                    }
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
        when (checkedId) {
            R.id.rb_boy -> {
                viewModel!!.model.sex = "1"
            }
            R.id.rb_girl -> {
                viewModel!!.model.sex = "0"
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
        when (requestCode) {
            0 -> {
                if (PictureSelector.obtainMultipleResult(data).isNotEmpty()) {
                    for (model: LocalMedia in PictureSelector.obtainMultipleResult(data)) {
                        ImageList.add(ImageList.size, model)
                    }
                    val file = File(ImageList[0].compressPath)
                    //加载图片
                    Glide.with(this).load(file).into(iv_header)
                }
            }
            1 -> {//我的类型
                abLog.e("我的类型", data.getStringExtra("lable"))
                viewModel!!.model.type = data.getStringExtra("lable")
                tv_mytype.text = viewModel!!.model.type
            }
            2 -> {//兴趣爱好
                abLog.e("兴趣爱好", data.getStringExtra("lable"))
                viewModel!!.model.interests = data.getStringExtra("lable")
                tv_hobby.text = viewModel!!.model.interests
            }
            3 -> {//地点标签
                abLog.e("地点标签", data.getStringExtra("lable"))
                viewModel!!.model.locales = data.getStringExtra("lable")
                tv_label.text = viewModel!!.model.locales
            }
            4 -> {//他的类型
                abLog.e("他的类型", data.getStringExtra("lable"))
                viewModel!!.model.type2 = data.getStringExtra("lable")
                tv_he_type.text = viewModel!!.model.type2
            }
        }
    }

    override fun uoLoad(url: String) {

    }

    override fun uoLoad(url: List<String>) {
        ProgressDialogUtil.dismissDialog()
        abLog.e("上传图片完成", Gson().toJson(url))
        val sb = StringBuffer()
        for (i in 0 until url.size) {
            sb.append(url[i] + "|")
        }

        viewModel!!.model.icons = sb.toString().substring(0, sb.toString().length - 1)
        viewModel!!.saveData().bindLifeCycle(this).subscribe({}, { toastFailure(it) })
    }


}