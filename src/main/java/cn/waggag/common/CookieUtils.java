package cn.waggag.common;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @description: 对于Cookie的基本操作
 * @author: waggag
 * @time: 2019/8/30 0:01
 * @Company http://www.waggag.cn
 */
public class CookieUtils {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * 获取指定Cookie的值，不编码
     * @param request    请求参数
     * @param cookieName cookie名
     * @return 返回cookie的Value
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        return getCookieValue(request, cookieName, false);
    }

    /**
     * 获取指定Cookie的值,可设置是否以UTF-8编码
     *
     * @param request    请求参数
     * @param cookieName cookie名
     * @param isDecoder  是否以UTF-8编码，True为编码
     * @return 返回cookie的Value
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isDecoder) {
        Cookie[] cookieList = request.getCookies();
        if (cookieList == null || cookieName == null) {
            return null;
        }
        String cookieValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    if (isDecoder) {
                        cookieValue = URLDecoder.decode(cookieList[i].getValue(), "UTF-8");
                    } else {
                        cookieValue = cookieList[i].getValue();
                    }
                    break;
                }
            }
        } catch (UnsupportedEncodingException exception) {
            logger.error("Cookie Decode Error.", exception);
        }
        return cookieValue;
    }

    /**
     * 以指定的编码形式获取Cookie
     *
     * @param request      请求参数
     * @param cookieName   cookie名
     * @param encodeString 指定的编码格式
     * @return cookie的值
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName, String encodeString) {
        Cookie[] cookieList = request.getCookies();
        if (ArrayUtils.isEmpty(cookieList) || cookieName == null) {
            return null;
        }
        String cookieValue = null;
        try {
            for (int i = 0; i < cookieList.length; i++) {
                if (cookieList[i].getName().equals(cookieName)) {
                    cookieValue = URLDecoder.decode(cookieList[i].getValue(), encodeString);
                    break;
                }
            }
        } catch (UnsupportedEncodingException exception) {
            logger.error("Cookie Decode Error.", exception);
        }
        return cookieValue;
    }


    /**
     * 根据请求得到cookie所属的域名
     * @param request 请求参数
     * @return 返回所属的域名
     */
    private static final String getDomainName(HttpServletRequest request) {
        String domainName = null;
        String serverName = request.getRequestURL().toString();
        if (serverName == null || serverName.equals("")) {
            domainName = "";
        } else {
            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                domainName = domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }
        }

        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
}
