package com.study.springbootdemo;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//启动完整 Spring 容器（含拦截器、Service、MyBatis）
@SpringBootTest
//注入 MockMvc
@AutoConfigureMockMvc
class BookApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @Test
    void login_and_get_books() throws Exception {
        // 1. 登录（假设数据库里已有用户 testuser / 123456）
        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content("{\"username\":\"测试号\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 2. 从 JSON 里取出 token（可以用 jsonPath 或简单字符串处理）
        // 简单做法：用 JsonPath 或 ObjectMapper 解析 loginResponse 取 data.token
        String token = JsonPath.read(loginResponse, "$.data.token");

        // 3. 带 Token 访问 /books/page
        mockMvc.perform(get("/books/page")
                        .param("page", "1")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.list").isArray());


    }


    @Test
    void get_books_without_token_should_401() throws Exception {
        mockMvc.perform(get("/books/page")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));
    }

}