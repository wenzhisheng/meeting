package com.meeting.common.aspect;

import com.meeting.common.exception.ResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author: dameizi
 * @description: 统一返回切面
 * @dateTime 2019-03-29 20:26
 * @className AllResultAspect
 */
@Aspect
@Component
public class AllResultAspect {

    /**
     * @author: dameizi
     * @dateTime: 2019-03-29 20:28
     * @description: controller拦截
     * @param: [proceedingJoinPoint]
     * @return: java.lang.Object
     */
    @Around("execution(* com.*..controller.*.*(..))&&(!@annotation(com.meeting.common.annotation.OtherReturn))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取目标方法
        Method proxyMethod = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Method targetMethod = proceedingJoinPoint.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        boolean isPublic = Modifier.isPublic(targetMethod.getModifiers());
        if(!isPublic){
            return proceedingJoinPoint.proceed();
        }
        // 获取返回信息
        Object result = proceedingJoinPoint.proceed();
        //如果返回值 带有ERROR:前缀 则认为是错误信息
        if(result instanceof String && ((String) result).startsWith("ERROR:")){
            String[] resultStr = ((String) result).split(":");
            return ResultUtil.error(-1,resultStr[1]);
        }else{
            return ResultUtil.success(result);
        }
    }

}
