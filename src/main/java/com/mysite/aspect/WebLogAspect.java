package com.mysite.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 请求的日志处理
 * Created by Donghua.Chen on 2018/4/28.
 */
@Aspect
@Component
public class WebLogAspect {

    private static Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切入com.xhx.springboot.controller包下的所有类的所有方法，方法参数不限，返回类型不限。
     * 第一个*代表返回类型不限，第二个*表示所有类，第三个*表示所有方法，..两个点表示方法里的参数不限。
     */
    @Pointcut("execution(public * com.mysite.controller.*.*(..))")
    public void webLog() {
    }


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        startTime.set(System.currentTimeMillis());

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        HttpSession session = request.getSession();
        // 记录下请求内容
        LOGGER.info("URL : " + request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD : " + request.getMethod());
        LOGGER.info("IP : " + request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置返回
     * 如果第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning：限定了只有目标方法返回值与通知方法参数类型匹配时才能执行后置返回通知，否则不执行，
     * 参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) {
        // 处理完请求，返回内容
        LOGGER.info("RESPONSE : " + result);
        LOGGER.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
        startTime.remove();//用完之后记得清除，不然可能导致内存泄露;
    }

}
