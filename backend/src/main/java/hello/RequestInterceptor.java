package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Component
public class RequestInterceptor
        extends HandlerInterceptorAdapter {


    Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {
        logger.info("API:"+request.getRequestURI()+";COOKIE:"+request.getHeader("Cookie")+";RESPONSESTATUS:"+response.getStatus() );

        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

    }
}