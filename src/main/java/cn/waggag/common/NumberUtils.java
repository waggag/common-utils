package cn.waggag.common;

import java.util.Random;

/**
 * @description: 常用数字的工具类
 * @author: waggag
 * @time: 2019/8/29 23:41
 * @Company http://www.waggag.cn
 */
public class NumberUtils {

    /**
     * 判断一个Double类型是否为Int类型
     * @param num 判断的数字
     * @return 返回True代表可以转为Int类型
     */
    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /**
     * 判断字符串是否是数值格式
     * @param str 字符串
     * @return 如果为True代表字符串为数值类型
     */
    public static boolean isDigit(String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 生成指定位数的随机数字，最大长度为8位随机数字
     * @param length 随机数字长度
     * @return 返回一个随机数字的字符串
     */
    public static String generateVerifyCode(int length) {
        length = Math.min(length, 8);
        int min = Double.valueOf(Math.pow(10, length - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, length + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, length);
    }

}
