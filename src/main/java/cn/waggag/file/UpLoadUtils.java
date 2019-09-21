package cn.waggag.file;

import java.util.UUID;

/**
 * @description: 文件上传类的工具类
 * @author: waggag
 * @time: 2019/9/15
 * @Company http://www.waggag.cn
 */
public class UpLoadUtils {

    /**
     * 根据真实的文件名获取随机名称
     * @param realName 真实名称
     * @return 随机文件名
     */
    public static String getUUIDName(String realName) {
        //获取后缀名
        int index = realName.lastIndexOf(".");
        if (index == -1) {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase();
        } else {
            return UUID.randomUUID().toString().replace("-", "").toUpperCase() + realName.substring(index);
        }
    }

    /**
     * 获取文件真实名称（包含后缀）,Windows和Linux不能通用，需要判断后实现
     * @param filePath 文件所在目录（包含文件名）
     * @return 文件真实名称
     */
    public static String getRealName(String filePath) {
        // c:/upload/1.jpg    1.jpg
        //获取最后一个"/"所在的索引
        int index = filePath.lastIndexOf("/");  //TODO 跨平台实现
        return filePath.substring(index + 1);
    }

    /**
     * 用户获取随机文件目录
     * @param fileName 文件名称
     * @return 随机两层目录
     */
    public static String getDir(String fileName) {
        int i = fileName.hashCode();
        String hex = Integer.toHexString(i);
        int j = hex.length();
        for (int k = 0; k < 8 - j; k++) {
            hex = "0" + hex;
        }
        return "\\" + hex.charAt(0) + "\\" + hex.charAt(1)+"\\";
    }
}
