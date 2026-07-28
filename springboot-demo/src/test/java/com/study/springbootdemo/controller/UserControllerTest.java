package com.study.springbootdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.study.springbootdemo.exception.GlobalExceptionHandler;
import com.study.springbootdemo.service.UserService;
import com.study.springbootdemo.vo.UserVo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class) //只启动 UserController 相关的 Web 层
@Import({UserController.class, GlobalExceptionHandler.class})
class UserControllerTest {

    @Autowired  //Spring 注入模拟 HTTP 客户端
    private MockMvc mockMvc;

    @MockitoBean //用假 Service 替代真 Service，避免连 DB
    private UserService userService;

    @Test
    void register_success() throws Exception {
        UserVo mockUser = new UserVo();
        mockUser.setId(1);
        mockUser.setUsername("testuser");

        when(userService.userRegister(any())).thenReturn(mockUser);

        mockMvc.perform(post("/auth/register")  //模拟 POST 请求
                .contentType("application/json")
                .content("{\"username\":\"testuser\",\"password\":\"123456\"}"))
                .andExpect(status().isOk()) //HTTP 状态码 200
                .andExpect(jsonPath("$.code").value(200)) //响应 JSON 里 code 为 200
                .andExpect(jsonPath("$.data.username").value("testuser"));
    }

    @Test
    void register_validation_fail() throws Exception {
        mockMvc.perform(post("/auth/register")
                .contentType("application/json")
                .content("{\"username\":\"\",\"password\":\"123456\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));
    }
}