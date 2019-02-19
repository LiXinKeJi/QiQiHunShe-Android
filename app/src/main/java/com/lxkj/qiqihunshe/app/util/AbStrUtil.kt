/*
 * Copyright (C) 2012 www.amsoft.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain activity copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lxkj.qiqihunshe.app.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern


// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbStrUtil.java
 * 描述：字符串处理类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-17 下午11:52:13
 */
object AbStrUtil {


    /**
     * 加载text内容
     */
    fun setText(tv: TextView, str: String?) {
        if (str == null) {
            tv.text = ""
        }
        tv.text = str
    }


    /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    fun parseEmpty(str: String?): String {
        var str = str
        if (str == null || "null" == str.trim { it <= ' ' }) {
            str = ""
        }
        return str.trim { it <= ' ' }
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     * 为空是true，非空false
     *
     * @param str 指定的字符串
     * @return true or false
     */
    fun isEmpty(str: String?): Boolean {
        return str == null || str.trim { it <= ' ' }.length == 0
    }

    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    fun chineseLength(str: String): Int {
        var valueLength = 0
        val chinese = "[\u0391-\uFFE5]"
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (!isEmpty(str)) {
            for (i in 0 until str.length) {
                /* 获取一个字符 */
                val temp = str.substring(i, i + 1)
                /* 判断是否为中文字符 */
                if (temp.matches(chinese.toRegex())) {
                    valueLength += 2
                }
            }
        }
        return valueLength
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    fun strLength(str: String): Int {
        var valueLength = 0
        val chinese = "[\u0391-\uFFE5]"
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (i in 0 until str.length) {
                //获取一个字符
                val temp = str.substring(i, i + 1)
                //判断是否为中文字符
                if (temp.matches(chinese.toRegex())) {
                    //中文字符长度为2
                    valueLength += 2
                } else {
                    //其他字符长度为1
                    valueLength += 1
                }
            }
        }
        return valueLength
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str  指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    fun subStringLength(str: String, maxL: Int): Int {
        var currentIndex = 0
        var valueLength = 0
        val chinese = "[\u0391-\uFFE5]"
        //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (i in 0 until str.length) {
            //获取一个字符
            val temp = str.substring(i, i + 1)
            //判断是否为中文字符
            if (temp.matches(chinese.toRegex())) {
                //中文字符长度为2
                valueLength += 2
            } else {
                //其他字符长度为1
                valueLength += 1
            }
            if (valueLength >= maxL) {
                currentIndex = i
                break
            }
        }
        return currentIndex
    }

    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
    fun isMobileNo(str: String): Boolean? {
        var isMobileNo: Boolean? = false
        try {
            val p = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(18[0-9])|(17[0,1,3,5-8]))\\d{8}$")
            val m = p.matcher(str)
            isMobileNo = m.matches()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isMobileNo
    }

    /**
     * 描述：是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    fun isNumberLetter(str: String): Boolean? {
        var isNoLetter: Boolean? = false
        val expr = "^[A-Za-z0-9]+$"
        if (str.matches(expr.toRegex())) {
            isNoLetter = true
        }
        return isNoLetter
    }

    /**
     * 描述：是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    fun isNumber(str: String): Boolean? {
        var isNumber: Boolean? = false
        val expr = "^[0-9]+$"
        if (str.matches(expr.toRegex())) {
            isNumber = true
        }
        return isNumber
    }

    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    fun isEmail(str: String): Boolean? {
        var isEmail: Boolean? = false
        val expr = "^([activity-z0-9A-Z]+[-|\\.]?)+[activity-z0-9A-Z]@([activity-z0-9A-Z]+(-[activity-z0-9A-Z]+)?\\.)+[activity-zA-Z]{2,}$"
        if (str.matches(expr.toRegex())) {
            isEmail = true
        }
        return isEmail
    }

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    fun isChinese(str: String): Boolean? {
        var isChinese: Boolean? = true
        val chinese = "[\u0391-\uFFE5]"
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (i in 0 until str.length) {
                //获取一个字符
                val temp = str.substring(i, i + 1)
                //判断是否为中文字符
                if (temp.matches(chinese.toRegex())) {
                } else {
                    isChinese = false
                }
            }
        }
        return isChinese
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    fun isContainChinese(str: String): Boolean? {
        var isChinese: Boolean? = false
        val chinese = "[\u0391-\uFFE5]"
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (i in 0 until str.length) {
                //获取一个字符
                val temp = str.substring(i, i + 1)
                //判断是否为中文字符
                if (temp.matches(chinese.toRegex())) {
                    isChinese = true
                } else {

                }
            }
        }
        return isChinese
    }


    /**
     * 描述：标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    fun dateTimeFormat(dateTime: String): String? {
        val sb = StringBuilder()
        try {
            if (isEmpty(dateTime)) {
                return null
            }
            val dateAndTime = dateTime.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (dateAndTime.size > 0) {
                for (str in dateAndTime) {
                    if (str.indexOf("-") != -1) {
                        val date = str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (i in date.indices) {
                            val str1 = date[i]
                            sb.append(strFormat2(str1))
                            if (i < date.size - 1) {
                                sb.append("-")
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ")
                        val date = str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (i in date.indices) {
                            val str1 = date[i]
                            sb.append(strFormat2(str1))
                            if (i < date.size - 1) {
                                sb.append(":")
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        return sb.toString()
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    fun strFormat2(str: String): String {
        var str = str
        try {
            if (str.length <= 1) {
                str = "0$str"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return str
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    文本
     * @param length 字节长度
     * @param dot    省略符号
     * @return 截取后的字符串
     */
    @JvmOverloads
    fun cutString(str: String, length: Int, dot: String? = ""): String {
        val strBLen = strlen(str, "GBK")
        if (strBLen <= length) {
            return str
        }
        var temp = 0
        val sb = StringBuffer(length)
        val ch = str.toCharArray()
        for (c in ch) {
            sb.append(c)
            if (c.toInt() > 256) {
                temp += 2
            } else {
                temp += 1
            }
            if (temp >= length) {
                if (dot != null) {
                    sb.append(dot)
                }
                break
            }
        }
        return sb.toString()
    }

    /**
     * 描述：截取字符串从第一个指定字符.
     *
     * @param str1   原文本
     * @param str2   指定字符
     * @param offset 偏移的索引
     * @return 截取后的字符串
     */
    fun cutStringFromChar(str1: String, str2: String, offset: Int): String {
        if (isEmpty(str1)) {
            return ""
        }
        val start = str1.indexOf(str2)
        if (start != -1) {
            if (str1.length > start + offset) {
                return str1.substring(start + offset)
            }
        }
        return ""
    }

    /**
     * 描述：获取字节长度.
     *
     * @param str     文本
     * @param charset 字符集（GBK）
     * @return the int
     */
    fun strlen(str: String?, charset: String): Int {
        if (str == null || str.length == 0) {
            return 0
        }
        var length = 0
        try {
            length = str.toByteArray(charset(charset)).size
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return length
    }

    /**
     * 获取大小的描述.
     *
     * @param size 字节个数
     * @return 大小的描述
     */
    fun getSizeDesc(size: Long): String {
        var size = size
        var suffix = "B"
        if (size >= 1024) {
            suffix = "K"
            size = size shr 10
            if (size >= 1024) {
                suffix = "M"
                //size /= 1024;
                size = size shr 10
                if (size >= 1024) {
                    suffix = "G"
                    size = size shr 10
                    //size /= 1024;
                }
            }
        }
        return size.toString() + suffix
    }

    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    fun ip2int(ip: String): Long {
        var ip = ip
        ip = ip.replace(".", ",")
        val items = ip.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return java.lang.Long.valueOf(items[0]) shl 24 or (java.lang.Long.valueOf(items[1]) shl 16) or (java.lang.Long.valueOf(
            items[2]
        ) shl 8) or java.lang.Long.valueOf(items[3])
    }


    /**
     * 获取textvew字符
     */
    fun tvTostr(tv: TextView): String {
        var str: String? = tv.text.toString().trim { it <= ' ' }
        if ((str == null) or (str!!.length == 0)) {
            str = ""
        }
        return str
    }


    /**
     * 获取edittext字符
     */
    fun etTostr(et: EditText): String {
        var str: String? = et.text.toString().trim { it <= ' ' }
        if ((str == null) or (str!!.length == 0)) {
            str = ""
        }
        return str
    }

    /**
     * 如果利率第一位是0，第二位不是小数点，去掉0
     */
    fun sub0(str: String): String {
        return if (str.substring(0, 1) == "0" && str.substring(1, 2) != ".") {
            str.substring(1, str.length)
        } else str
    }


    fun setDrawableLeft(context: Context, id: Int, tv: TextView, DrawablePaddin: Int) {
        if (id != -1) {
            val nav_up = context.resources.getDrawable(id)
            nav_up.setBounds(0, 0, nav_up.minimumWidth, nav_up.minimumHeight)
            tv.compoundDrawablePadding = DrawablePaddin
            tv.setCompoundDrawables(nav_up, null, null, null)
        } else {
            tv.setCompoundDrawables(null, null, null, null)
        }

    }

    fun setDrawableRight(context: Context, id: Int, tv: TextView, DrawablePaddin: Int) {
        if (id != -1) {
            val nav_up = context.resources.getDrawable(id)
            nav_up.setBounds(0, 0, nav_up.minimumWidth, nav_up.minimumHeight)
            tv.compoundDrawablePadding = DrawablePaddin

            tv.setCompoundDrawables(null, null, nav_up, null)
        } else {
            tv.setCompoundDrawables(null, null, null, null)
        }

    }

    fun setDrawableRight(context: Context, id: Int, tv: RadioButton, DrawablePaddin: Int) {
        if (id != -1) {
            val nav_up = context.resources.getDrawable(id)
            nav_up.setBounds(0, 0, nav_up.minimumWidth, nav_up.minimumHeight)
            tv.compoundDrawablePadding = DrawablePaddin

            tv.setCompoundDrawables(null, null, nav_up, null)
        } else {
            tv.setCompoundDrawables(null, null, null, null)
        }

    }





    fun setDrawableTop(context: Context, id: Int, tv: TextView, DrawablePaddin: Int) {
        if (id != -1) {
            val nav_up = context.resources.getDrawable(id)
            nav_up.setBounds(0, 0, nav_up.minimumWidth, nav_up.minimumHeight)
            tv.compoundDrawablePadding = DrawablePaddin
            tv.setCompoundDrawables(null, nav_up, null, null)
        } else {
            tv.setCompoundDrawables(null, null, null, null)
        }

    }

    /**
     * 禁止搬编辑edittext
     */
    fun NoEditText(et: EditText) {
        et.isFocusable = false
        et.isFocusableInTouchMode = false // user touches widget on phone with touch screen
        et.isClickable = false
    }

}
/**
 * 描述：截取字符串到指定字节长度.
 *
 * @param str    the str
 * @param length 指定字节长度
 * @return 截取后的字符串
 */
