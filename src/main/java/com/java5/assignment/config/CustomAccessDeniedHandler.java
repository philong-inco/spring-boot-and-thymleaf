package com.java5.assignment.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // lấy url cũ lại và cắt bỏ param nếu có oki
        String refererUrl = request.getHeader("Referer");
        String errorMessage = "true";
        if (refererUrl != null){
            if (refererUrl.contains("?"))
                refererUrl = refererUrl.substring(0, refererUrl.indexOf("?"));
            response.sendRedirect(refererUrl + "?error=" + errorMessage);
        } else {
            response.sendRedirect("/access-denied" + "?error=" + errorMessage);
        }
    }
}
