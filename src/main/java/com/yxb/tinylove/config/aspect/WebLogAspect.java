package com.yxb.tinylove.config.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * http请求响应参数日志记录
 *
 * @author yxb
 * @since 2019/12/9
 */
@Component
@Aspect
@Slf4j
public class WebLogAspect {

    @Pointcut("@annotation( org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation( org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation( org.springframework.web.bind.annotation.PostMapping)")
    public void PointCut() {
    }

    @Before("PointCut()")
    public void before(JoinPoint joinPoint) {
        if (log.isDebugEnabled()) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String sourceIp = getSourceIp(request);
                log.debug("---------->请求地址：{}", request.getRequestURL().toString());
                log.debug("---------->请求方式：{}", request.getMethod());
                log.debug("---------->请求方IP：{}", sourceIp);
                log.debug("---------->传入参数：{}", JSON.toJSONString(filterArgs(joinPoint.getArgs())));
            }
        }

    }

    @AfterReturning(pointcut = "PointCut()", returning = "result")
    public void after(Object result) {
        if (log.isDebugEnabled()) {
            log.debug("---------->传出参数：{}", JSON.toJSONString(result));
        }
    }

    @AfterThrowing(pointcut = "PointCut()", throwing = "e")
    public void exception(Throwable e) {
        if (log.isDebugEnabled()) {
            log.error("---------->异常信息：{}", e.getMessage());
        }
    }


    /**
     * 过滤特殊参数
     *
     * @param args args
     * @return result
     */
    private List<Object> filterArgs(Object[] args) {
        List<Object> result = new ArrayList<>();
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    continue;
                }
                if (arg instanceof HttpServletResponse) {
                    continue;
                }
                result.add(arg);
            }
        }
        return result;
    }

    private String getSourceIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
