package org.loggerpoc.framework.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPointcuts {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {}

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void servicePointcut() {}

    @Pointcut("within(@org.springframework.stereotype.Repository *)")
    public void repoPointcut() {}

    @Pointcut("execution(* org.loggerpoc.app..*(..))")
    public void appPointcut() {}

    @Pointcut("appPointcut() && (controllerPointcut() || servicePointcut() || repoPointcut())")
    public void mainPointcut() {}

}
