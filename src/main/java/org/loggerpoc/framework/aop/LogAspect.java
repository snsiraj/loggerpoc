package org.loggerpoc.framework.aop;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Stream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.loggerpoc.framework.dao.LogDetailDao;
import org.loggerpoc.framework.entity.LoggerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(0)
@Aspect
@Configuration

public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LogDetailDao logDetailDao;


    @Around("org.loggerpoc.framework.aop.AppPointcuts.mainPointcut()")
    public Object calculateMethodTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        final Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        if(!log.isDebugEnabled()) {
            return joinPoint.proceed();

        }

        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = ((MethodSignature)joinPoint.getSignature()).getMethod().getName();
        String methodArgs = Stream.of(joinPoint.getArgs()).toList().toString();
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - startTime;
        LoggerMessage loggerMessage = LoggerMessage.builder()
                .transactionId(request.getAttribute("X-App-Name").toString())
                .className(className)
                .methodName(methodName)
                .methodArgs(methodArgs)
                .executionTimeInMillis(executionTime)
                .result(new ObjectMapper().writeValueAsString(result))
                .build();
        log.debug("Transaction: {}", loggerMessage.toString());
        logDetailDao.save(loggerMessage);
        return result;

    }

}
