package com.study.springbootdemo.config;

import com.study.springbootdemo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//拦截器写好了，Spring 不会自动启用，要在配置里 注册。
@Configuration // 配置类 标识这个类是 Spring 的配置类 可以被Spring容器管理 启用这个配置类 使拦截器生效
// WebMvcConfigurer Spring MVC 提供的 扩展接口，专门给开发者加拦截器、CORS 等
/*
 * Spring Boot 启动时会做类似的事：
 * 
 * 1. 扫描所有 @Configuration
 * 2. 发现 WebMvcConfig 实现了 WebMvcConfigurer
 * 3. 调用它的 addInterceptors(registry)
 * 4. 你把 loginInterceptor 注册进 registry
 * 5. Spring MVC 内部保存：哪些路径 → 哪些拦截器
 */
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    public WebMvcConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(loginInterceptor)
    //             .addPathPatterns("/**") // 拦截所有路径
    //             .excludePathPatterns( // 以下不拦截（白名单）
    //                     "/auth/login",
    //                     "/auth/register",
    //                     "/error",
    //                     "/swagger-ui/**",
    //                     "/swagger-ui.html",
    //                     "/v3/api-docs/**",
    //                     "/v3/api-docs",
    //                     "/actuator/**");
    // }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口都允许跨域
                .allowedOrigins("http://localhost:3000")// 前端地址，按你实际端口改
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法
                .allowedHeaders("*") // 允许的请求头
                .allowCredentials(true) // 允许携带凭证 cookie
                .maxAge(3600); // 缓存时间
    }
}
