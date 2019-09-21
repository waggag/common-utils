package cn.waggag.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @description: 对于请求使用Filter进行统一的编码
 * @author: waggag
 * @time: 2019/9/15
 * @Company http://www.waggag.cn
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //1.强转
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //2.放行
        chain.doFilter(new EncodeRequest(request), response);
    }

    @Override
    public void destroy() {

    }
}

class EncodeRequest extends HttpServletRequestWrapper {
    //请求处理
    private HttpServletRequest request;
    private boolean flag = true;

    public EncodeRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    /**
     * 对于getParameter进行增强，获取值后判断值是否为空后再返回
     * @param name 参数名称
     * @return 返回参数对应的值
     */
    @Override
    public String getParameter(String name) {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        String[] values = request.getParameterValues(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 根据参数名获取所有的参数值
     * @param name 参数名
     * @return 字符串数据（存放参数所有的值）
     */
    @Override
    public String[] getParameterValues(String name) {
        if (name == null || name.trim().length() == 0) {
            return null;
        }
        Map<String, String[]> map = getParameterMap();
        if (map == null || map.size() == 0) {
            return null;
        }
        return map.get(name);
    }

    /**
     * map{ username=[tom],password=[123],hobby=[eat,drink]}
     * 获取所有的参数，转为map类型
     * @return 编码过后的值，后台无需对其解析，取出可直接使用
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        String method = request.getMethod();
        if ("Post".equalsIgnoreCase(method)) {
            try {
                request.setCharacterEncoding("utf-8");
                return request.getParameterMap();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if ("Get".equalsIgnoreCase(method)) {
            Map<String, String[]> map = request.getParameterMap();
            for (String key : map.keySet()) {
                String[] arr = map.get(key);
                //继续遍历数组
                for (int i = 0; i < arr.length; i++) {
                    try {
                        arr[i] = new String(arr[i].getBytes("iso8859-1"), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            //需要遍历map 修改value的每一个数据的编码
            return map;
        }
        //只对于Post和Get请求进行处理，别的请求返回原始值
        return super.getParameterMap();
    }
}

