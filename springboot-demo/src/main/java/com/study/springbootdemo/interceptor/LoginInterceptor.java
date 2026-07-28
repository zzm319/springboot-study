package com.study.springbootdemo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import com.study.springbootdemo.common.BusinessException;
import com.study.springbootdemo.common.ResultCode;
import com.study.springbootdemo.util.JwtUtil;
import io.jsonwebtoken.Claims;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    // 构造器注入JwtUtil
    private final JwtUtil jwtUtil;
    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            // OPTIONS 请求直接放行（为以后 CORS 做准备）
            return true;
        }
        // 获取请求头中的token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("未携带 Token, uri={}", request.getRequestURI());
            throw new BusinessException(ResultCode.UNAUTHORIZED, "unauthorized");
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            logger.warn("Token 验证失败, uri={}", request.getRequestURI());
            throw new BusinessException(ResultCode.UNAUTHORIZED, "登录已过期");
        }
        Claims claims = jwtUtil.parseToken(token);
        int userId = claims.get("userId", Integer.class);
        String userName = claims.getSubject();
        request.setAttribute("userId", userId);
        request.setAttribute("userName", userName);
        logger.debug("Token 验证成功, userId={}, userName={}, uri={}", userId, userName, request.getRequestURI());
        return true;
    }
}
