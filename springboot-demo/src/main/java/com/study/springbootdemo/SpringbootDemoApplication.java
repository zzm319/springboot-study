package com.study.springbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication  //@SpringBootApplication = 开启自动配置 + 组件扫描，扫描范围：启动类所在包及其子包（com.study.springbootdemo.*）
/**
 * SpringBootApplication是个组合注解，包含以下三个注解：
 * @SpringBootConfiguration：标示这是一个 Spring Boot 应用的配置类
 * @EnableAutoConfiguration：开启自动配置
 * @ComponentScan：组件扫描，扫描范围：启动类所在包及其子包（com.study.springbootdemo.*）
 */
@MapperScan("com.study.springbootdemo.mapper") //加了这个，就不用每个mapper类都加@Mapper就可以注册为bean了
@EnableCaching //开启缓存。Spring 的 @Cacheable 等注解才会生效。
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }

}
