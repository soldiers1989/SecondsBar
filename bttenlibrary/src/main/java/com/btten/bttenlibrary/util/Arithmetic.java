package com.btten.bttenlibrary.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 各种数学运算工具类
 */
public class Arithmetic
{
    /**
     * 默认算出来的值，保留10位小数精度
     */
    private static final int SCALE = 10;

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static float add(float v1, float v2)
    {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static float sub(float v1, float v2)
    {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */

    public static float mul(float v1, float v2)
    {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.multiply(b2).floatValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */

    public static double mul(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */

    public static double mul(double v1, double v2, int scale)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return giveUp(b1.multiply(b2).doubleValue(), scale);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */

    public static double div(double v1, double v2)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, SCALE, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    public static float div(float v1, float v2, int scale)
    {
        if (scale < 0)
        {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).floatValue();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    public static double div(double v1, double v2, int scale)
    {
        if (scale < 0)
        {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();

    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(float v, int scale)
    {
        if (scale < 0)
        {
            scale = 0;
        }
        BigDecimal b = new BigDecimal(Float.toString(v));
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 将scale之外的数全部舍弃
     *
     * @param v     需要舍弃的数字
     * @param scale 小数点后保留几位
     * @return 舍弃后的结果
     */
    public static double giveUp(double v, int scale)
    {
        if (scale < 0)
        {
            scale = 0;
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 浮点转字符串，不显示科学计数法
     *
     * @param value 需要转换的数值
     */
    public static String doubleToStr(double value)
    {
        return doubleToStr(value, 2);
    }

    /**
     * 浮点转字符串，不显示科学计数法
     *
     * @param value 需要转换的数值
     * @param point 保存小数位数
     */
    public static String doubleToStr(double value, int point)
    {
        return doubleToStr(value, point, RoundingMode.DOWN);
    }

    /**
     * 浮点转字符串，不显示科学记数法，支持设置舍入模式
     *
     * @param value 浮点值
     * @param point 精度
     * @param mode  舍入模式
     * @return
     */
    public static String doubleToStr(double value, int point, RoundingMode mode)
    {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(point);
        df.setGroupingUsed(false);
        df.setRoundingMode(mode);
        return df.format(value);
    }

    /**
     * 浮点转字符串，不显示科学计数法，始终显示两位小数
     *
     * @param value
     * @return
     */
    public static String doubleToDoublePoint(double value)
    {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(value);
    }
}
