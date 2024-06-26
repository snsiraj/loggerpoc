package org.loggerpoc.framework.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.web.servlet.HandlerInterceptor;

public class CustomHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = java.util.UUID.randomUUID().toString();
        String header = Optional.ofNullable(request.getHeader("X-App-Name")).orElse(uuid);
        request.setAttribute("X-App-Name", header);
        response.addHeader("X-App-Name", header);

        return true;
    }

}
