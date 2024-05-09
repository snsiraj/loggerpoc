package org.loggerpoc.loggepoc.framework.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.loggerpoc.loggepoc.framework.dao.LoggingDao;
import org.loggerpoc.loggepoc.framework.entity.LogReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@Slf4j
public class LogOncePerReqFilter extends OncePerRequestFilter {

    @Autowired
    LoggingDao loggingDao;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);


        String requestBody = getValueAsString(cachingRequestWrapper.getContentAsByteArray(), cachingRequestWrapper.getCharacterEncoding());
        String responseBody = getValueAsString(cachingResponseWrapper.getContentAsByteArray(), cachingResponseWrapper.getCharacterEncoding());

        logReqRes(requestBody, responseBody, cachingRequestWrapper.getRequestURI(), cachingRequestWrapper.getMethod(), response.getStatus(), request, response);

        cachingResponseWrapper.copyBodyToResponse();
    }

    private String getValueAsString(byte[] contentAsByteArray, String characterEncoding) {
        String dataAsString = "";
        try {
            dataAsString = new String(contentAsByteArray, characterEncoding);
        } catch (Exception e) {
            log.error("Exception occurred while converting byte into an array: {}", e.getMessage());
            e.printStackTrace();
        }
        return dataAsString;
    }

    @Async
    protected void logReqRes(String request, String response, String uri, String httpMethod, int status, HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
        LogReqRes logReqRes = LogReqRes.builder()
                .request(request)
                .response(response)
                .uri(uri).httpMethod(httpMethod)
                .status(status)
                .transactionId((String) req.getAttribute("X-App-Name"))
                .build();

        String logReqResString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(logReqRes);

        if (!uri.contains("/h2-console/")) {
            log.debug("Logging request and response: {}", logReqResString);
            loggingDao.save(logReqRes);
        }
    }

}
