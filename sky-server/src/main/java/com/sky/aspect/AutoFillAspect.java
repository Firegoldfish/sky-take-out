package com.sky.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

// 自动填充切面，用于自动填充实体类中的createTime和updateTime字段
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * 自动填充切面
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointcut() {

    }
    /**
     * 前置通知自动填充
     */
    @Before("autoFillPointcut()")
    public void AutoFill(){
        log.info("自动填充切面执行...");
    }
}
