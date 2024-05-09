package com.applogger.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class CustomHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = java.util.UUID.randomUUID().toString();

        if(request.getHeader("X-App-Name") == null) {
            request.setAttribute("X-App-Name", uuid);
            response.addHeader("X-App-Name", (String) request.getAttribute("X-App-Name"));
        }
        return true;
    }

}
