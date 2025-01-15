package cn.flamingbird.reporter.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

public class HttpServletUtils {

    public static HttpServletRequest getHttpServletRequest() {
        // 获取当前请求的属性对象
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            // 根据属性对象获取 HttpServletRequest
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static HttpServletResponse getHttpServletResponse() {
        // 获取当前请求的属性对象
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            // 根据属性对象获取 HttpServletRequest
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        return null;
    }

    public static String readRequestBody(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
            byte[] contentAsByteArray = requestWrapper.getContentAsByteArray();
            String body = new String(contentAsByteArray, StandardCharsets.UTF_8);
            return body;
        }
        return null;
    }

    public static String readResponseBody(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper) {
            ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) response;
            byte[] contentAsByteArray = responseWrapper.getContentAsByteArray();
            String body = new String(contentAsByteArray, StandardCharsets.UTF_8);
            return body;
        }
        return null;
    }


}