package org.loggerpoc.framework.aop;


import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.loggerpoc.framework.annotation.AnnotationProcessor;
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

  private Logger getTargetLogger(JoinPoint joinPoint) {
    return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
  }

  @Around("org.loggerpoc.framework.aop.AppPointcuts.mainPointcut()")
  public Object calculateMethodTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    if (!getTargetLogger(joinPoint).isDebugEnabled()) {
      return joinPoint.proceed();

    }
    long startTime = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    Long executionTime = System.currentTimeMillis() - startTime;
    logAndSave(joinPoint, Optional.ofNullable(result).orElse(""), executionTime);
    return result;
  }

  @AfterThrowing(value = "org.loggerpoc.framework.aop.AppPointcuts.appPointcut()", throwing = "ex")
  public void logAfterThrowingAllMethods(JoinPoint joinPoint, Exception ex) {
    logAndSave(joinPoint, ex, 0L);
  }

  private void logAndSave(JoinPoint joinPoint, Object result, long executionTime) {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
    String methodArgs = Stream.of(joinPoint.getArgs()).map(Object::toString)
        .collect(Collectors.joining(", "));
    LoggerMessage loggerMessage = LoggerMessage.builder()
        .transactionId(request.getAttribute("X-App-Name").toString())
        .className(className)
        .methodName(methodName)
        .methodArgs(AnnotationProcessor.processAnnotations(joinPoint.getArgs()[0]))
        .executionTimeInMillis(executionTime / 1000000) // convert to milliseconds
        .result(AnnotationProcessor.processAnnotations(result))
        .build();
    getTargetLogger(joinPoint).debug("Transaction: {}", loggerMessage);
    logDetailDao.save(loggerMessage);
    System.out.println();
  }


}
