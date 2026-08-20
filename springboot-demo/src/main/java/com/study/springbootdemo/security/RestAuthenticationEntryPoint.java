package com.study.springbootdemo.security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.study.springbootdemo.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(
            HttpStatus.UNAUTHORIZED.value()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write( 
            new ObjectMapper().writeValueAsString(
                Result.error(HttpStatus.UNAUTHORIZED.value(), "Unauthorized")
            )
        );
    }
}
