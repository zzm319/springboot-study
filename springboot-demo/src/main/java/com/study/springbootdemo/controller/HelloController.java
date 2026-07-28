package com.study.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //标记这是一个 API 模块，返回值直接当 JSON/文本响应
/**
 * @RestController是个组合注解，包含以下两个注解：
 * @Controller：标记这是 MVC 的控制器，交给 Spring 管理（注册为 Bean）
 * @ResponseBody：方法返回值 直接写入 HTTP 响应体，不找视图模板
 */
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot!";
    }

    @GetMapping("/hi")
    public String hi() {
        return "Hi, Spring Boot!";
    }

}
