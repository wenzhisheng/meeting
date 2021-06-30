package com.meeting.common.exception;

/**
 * @dateTime 2019-03-26 17:53
 * @author: dameizi
 * @description: 自定义异常工具
 */
public class ResultUtil {

    /** Success Status */
    public static final int SUCCESS_STATUS = 1;
    /** Success Massage */
    public static final String SUCCESS_MSG = "SUCCESS";

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 17:56
     * @description: 成功返回传入返回体具体出參
     * @param: [object]
     * @return: com.weilaizhe.common.exception.Result
     */
    public static ResultInfo success(Object object){
        ResultInfo result = new ResultInfo();
        result.setStatus(SUCCESS_STATUS);
        result.setMsg(SUCCESS_MSG);
        result.setData(object);
        return result;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 17:56
     * @description: 提供给部分不需要出參的接口
     * @param: []
     * @return: com.weilaizhe.common.exception.Result
     */
    public static ResultInfo success(){
        return success(null);
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 17:56
     * @description: 自定义错误信息
     * @param: [code, msg]
     * @return: com.weilaizhe.common.exception.Result
     */
    public static ResultInfo error(Integer code, String msg){
        ResultInfo result = new ResultInfo();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    /**
     * @author: dameizi
     * @dateTime: 2019/3/26 0026 下午 17:57
     * @description: 返回已知范围内异常信息
     * @param: [exceptionEnum]
     * @return: com.weilaizhe.common.exception.Result
     */
    public static ResultInfo error(ExceptionEnum exceptionEnum){
        ResultInfo result = new ResultInfo();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMsg());
        result.setData(null);
        return result;
    }
}
