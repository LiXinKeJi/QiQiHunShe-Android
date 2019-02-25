package com.lxkj.qiqihunshe.app.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import java.io.*
import java.util.*

/**
 * Created by Slingge on 2018/4/13 0013.
 */
object ImageUtil {


    @SuppressLint("ObsoleteSdkInt")
    fun getVideoThumbnail(context: Context, filePath: String): Bitmap? {
        var bitma: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                if (Build.VERSION.SDK_INT >= 24) {
                    retriever.setDataSource(context, FileProvider.getUriForFile(context, "com.lixin.amuseadjacent.provider", File(filePath)))
                } else {
                    retriever.setDataSource(filePath, HashMap())
                }
            } else {
                retriever.setDataSource(filePath)
            }
            bitma = retriever.frameAtTime
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        return bitma
    }

    /**
     * 保存bitmap文件
     */
    fun saveFilePath(bitmap: Bitmap): File? {
        val tag: Int
        if (bitmap == null) {
            ToastUtil.showToast("图片错误")
            return null
        }


        val filePath = Environment.getExternalStorageDirectory().path + "/Android/data/com.lxkj.runproject/cache/image"
        val destDir = File(filePath)
        if (!destDir.exists()) {//如果不存在则创建
            destDir.mkdirs()
        }
        val name = "lowsource.jpg"
        val file = File(filePath, name)
        val bos: BufferedOutputStream
        try {
            bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            try {
                bos.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        bitmap.recycle()
        return file
    }


    //获取视频预览图
    fun getVideoThumbnail2(filePath: String): Bitmap? {
        val media = MediaMetadataRetriever()
        media.setDataSource(filePath)
        return media.frameAtTime
    }


}