package com.bifan.applib.utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 数据格式化工具
 */
public class FormatUtil {

    /**
     * @param floatV
     * @return 判断浮点数是否是整数
     */
    public static Boolean IsInt(float floatV) {
        float f = floatV;
        int i = (int) f;
        return i == f;
    }

    public static String getShowStr_KeepTwoDecimalPlaces(float floatV) {
        if (IsInt(floatV)) {
            return (int) floatV + "'";
        }
        return getFloat_KeepTwoDecimalPlaces(floatV) + "";
    }

    public static String getShowStr_KeepOneDecimalPlaces(float floatV) {
        if (IsInt(floatV)) {
            return (int) floatV + "'";
        }
        return getFloat_KeepOneDecimalPlaces(floatV) + "";
    }

    /**
     * @param floatV 保留两位小数点，舍入为四舍五入
     * @return
     */
    public static float getFloat_KeepTwoDecimalPlaces(float floatV) {
        String value = getFloatFormat_KeepTwoDecimalPlaces().format(floatV);
        float a = Float.valueOf(value);
        return a;
    }

    /**
     * @param doubleV 保留两位小数点，舍入为四舍五入
     * @return
     */
    public static float getFloat_KeepTwoDecimalPlaces(Double doubleV) {
        String value = getFloatFormat_KeepTwoDecimalPlaces().format(doubleV);
        float a = Float.valueOf(value);
        return a;
    }

    /**
     * @param floatV 保留1位小数点，舍入为四舍五入
     * @return
     */
    public static float getFloat_KeepOneDecimalPlaces(float floatV) {
        String value = getFloatFormat_KeepOneDecimalPlaces().format(floatV);
        float a = Float.valueOf(value);
        return a;
    }

    /**
     * @param doubleV 保留1位小数点，舍入为四舍五入
     * @return
     */
    public static float getFloat_KeepOneDecimalPlaces(Double doubleV) {
        String value = getFloatFormat_KeepOneDecimalPlaces().format(doubleV);
        float a = Float.valueOf(value);
        return a;
    }

    /**
     * @param floatV
     * @return 舍入为四舍五入
     * --------------------
     */
    public static int getInt(float floatV) {
        String value = getIntFormat().format(floatV);
        int a = Integer.valueOf(value);
        return a;
    }


    /**
     * @param doubleV
     * @return 舍入为四舍五入
     */
    public static int getInt(Double doubleV) {
        String value = getIntFormat().format(doubleV);
        int a = Integer.valueOf(value);
        return a;
    }

    private static DecimalFormat getFloatFormat_KeepTwoDecimalPlaces() {
        DecimalFormat df = new DecimalFormat("0.0#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return  df;
    }

    private static DecimalFormat getFloatFormat_KeepOneDecimalPlaces() {
        DecimalFormat df = new DecimalFormat("0.#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return  df;
    }

    private static DecimalFormat getIntFormat() {
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return  df;
    }

    public static Boolean isRightIp(String ip) {
        if (TextUtils.isEmpty(ip)) return false;

        String regex = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";

        // 匹配1 和匹配2均可实现Ip判断的效果
        Pattern pattern = Pattern.compile(regex);
        try {
            return pattern.matcher(ip).matches();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPortLegal(int port) {
        return port >= 1 && port <= 65535;
    }

    public static Boolean isUrlLegalL(String url) {
        return !TextUtils.isEmpty(url) && Patterns.WEB_URL.matcher(url).matches();
    }

}
