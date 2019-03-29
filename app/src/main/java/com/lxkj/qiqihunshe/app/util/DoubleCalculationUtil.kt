package com.lxkj.qiqihunshe.app.util

import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * double类型经度计算
 * Created by Slingge on 2017/7/18 0018.
 */

object DoubleCalculationUtil {

    /**
     * 提供精确加法计算的add方法
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    fun add(value1: Double, value2: Double): Double {
        val b1 = BigDecimal(value1.toString())
        val b2 = BigDecimal(value2.toString())
        return b1.add(b2).toDouble()
    }

    /**
     * 提供精确减法运算的sub方法
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    fun sub(value1: Double, value2: Double): Double {
        val b1 = BigDecimal(value1.toString())
        val b2 = BigDecimal(value2.toString())
        return b1.subtract(b2).toDouble()
    }

    /**
     * 提供精确乘法运算的mul方法
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    fun mul(value1: Double, value2: Double): Double {
        val b1 = BigDecimal(value1.toString())
        val b2 = BigDecimal(value2.toString())
        return b1.multiply(b2).toDouble()
    }

    /**
     * 提供精确的除法运算方法div
     * @param value1 被除数
     * @param value2 除数
     * @param scale 精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    @Throws(IllegalAccessException::class)
    fun div(value1: Double, value2: Double, scale: Int): Double {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw IllegalAccessException("精确度不能小于0")
        }
        val b1 = BigDecimal(value1.toString())
        val b2 = BigDecimal(value2.toString())
        return b1.divide(b2, scale).toDouble()
    }


    //距离，米转公里，保留2位小数
    fun mTokm(dis: String): String {
        return DecimalFormat("#0.00").format(dis.toDouble() / 1000).toString()+"km"
    }


}
