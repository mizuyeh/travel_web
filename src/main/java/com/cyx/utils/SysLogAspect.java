package com.cyx.utils;

import com.cyx.entity.SysLog;
import com.cyx.service.SysLogService;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @Description 通过aop获取访问的日志信息
 * @date 2021/3/5
 */
@Component
@Aspect
public class SysLogAspect {
    //开始访问时间
    private Date visitTime;
    //访问的类
    private Class visitClass;
    //访问方法
    private Method method;
    private String username;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("execution(* com.cyx.controller.*.*(..))")
    public void logPointCut(){}
    /**
     * 获取开始访问的时间，哪个类，方法名。
     * @Param [joinPoint]
     * @Return void
     */
    @Before("logPointCut()")
    public void beforeAccess(JoinPoint joinPoint) {
        System.out.println("--------------before日志开始记录");
        //此方法执行即为开始访问的时间
        visitTime = new Date();
        //获取访问的类
        visitClass = joinPoint.getTarget().getClass();
        //获取访问的方法名
        String methodName = joinPoint.getSignature().getName();
        method = currentMethod(joinPoint, methodName);
        //获取当前操作的对象
        username = (String) SecurityUtils.getSubject().getPrincipal();
    }

    @AfterReturning("logPointCut()")
    public void afterAccess(JoinPoint joinPoint) {
        System.out.println("--------------After日志开始记录");
        //如果before未获取到姓名，则在after获取
        if(username == null) {
            username = (String) SecurityUtils.getSubject().getPrincipal();
        }
        //访问时长是该方法执行时间-开始访问的时间
        Long executionTime = System.currentTimeMillis() - visitTime.getTime();

        //获取url
        //1. 先获取类上的@RequestMapping的值
        String url = null;
        RequestMapping classAnnotation = (RequestMapping) visitClass.getAnnotation(RequestMapping.class);
        if(classAnnotation != null) {
            String[] classValue = classAnnotation.value();

            //2. 获取方法上的@RequestMapping的值
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            if(methodAnnotation != null) {
                String[] methodValue = methodAnnotation.value();
                url = classValue[0] + methodValue[0];
            }
        }

        //获取ip地址
        String ip = request.getRemoteAddr();

        //将结果封装到sysLog中
        SysLog sysLog = new SysLog();
        sysLog.setExecutionTime(executionTime);
        sysLog.setIp(ip);
        sysLog.setMethod("[类名]: " + visitClass.getName()
                + ", [方法名]: " + method.getName());
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setVisitTime(visitTime);

        //将结果保存在数据库
        sysLogService.save(sysLog);
    }

    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        /**
         * 获取目标类的所有方法，找到当前要执行的方法
         */
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }
}
