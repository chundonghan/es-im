package com.es.es_im.common.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExcutionTimeAspect
{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Around(value = "execution(* com.es.chat.service..*Service*.*(..))")
    public Object logExcutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        Signature signature = joinPoint.getSignature();
        String clazzName = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        long startTime = System.currentTimeMillis();
        Object result =joinPoint.proceed();
        logger.info("@@Excution Time@@[" + clazzName + "##" + methodName + "]@@"
                + (System.currentTimeMillis() - startTime));
        return result;
    }
}
