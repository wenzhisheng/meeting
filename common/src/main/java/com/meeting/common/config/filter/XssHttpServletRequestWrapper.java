package com.meeting.common.config.filter;

import com.meeting.common.exception.DescribeException;
import com.meeting.common.exception.ExceptionEnum;
import com.meeting.common.util.SpringContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @dateTime 2019-03-26 22:39
 * @author: dameizi
 * @description: XSS攻击拦截器
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 22:55
     * @description: 对参数中特殊字符进行过滤
     * @param: [parameter]
     * @return: java.lang.String
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        return checkXSS(value);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 22:56
     * @description: 对数组参数进行特殊字符过滤
     * @param: [parameter]
     * @return: java.lang.String[]
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return values;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = checkXSS(values[i]);
        }
        return encodedValues;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019-03-28 16:27
     * @description: 判断非法字符
     * @param: [value]
     * @return: java.lang.String
     */
    private String checkXSS(String value) {
        if(StringUtils.isEmpty(value)) {
            return value;
        }
        //把请求参数过滤空格转换成小写
        String val = value.trim().replace(" ", "").toLowerCase();
        //获取配置的非法字符
        String str = SpringContextHolder.getApplicationContext().getEnvironment().getProperty("xss.array");
        String[] arr = str.split(",");
        for (String a : arr) {
            //如果存在
            if (val.indexOf(a) != -1) {
                throw new DescribeException(ExceptionEnum.PARAM_ERROR);
            }
        }
        return value;
    }

}
