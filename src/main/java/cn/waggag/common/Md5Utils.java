package cn.waggag.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @description: Md5加密方法
 * @author: waggag
 * @time: 2019/9/3 12:02
 * @Company http://www.waggag.cn
 */
public class Md5Utils {

    /**
     * 记录日志
     */
    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    /**
     * 获取MD5加密后的字符串
     * @param string 需要加密的字符串
     * @return Md5加密后的字符串
     */
    public static String getHash(String string) {
        try {
            return new String(toHex(getMd5(string)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception exception) {
            log.error("Not Supported Charset...{}", exception);
            return string;
        }
    }

    /**
     * 字符串转为字节数组
     * @param string 需要加密的字符串
     * @return 字节数组
     */
    private static byte[] getMd5(String string) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(string.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception exception) {
            log.error("MD5 Error...", exception);
        }
        return null;
    }

    /**
     * 将字节数组转为Hash值
     * @param hash 传入的字节数组
     * @return Md5加密后的字符串
     */
    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(hash.length * 2);
        for (int i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(hash[i] & 0xff, 16));
        }
        return stringBuffer.toString();
    }
}
