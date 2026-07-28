package com.study.springbootdemo.controller;

import com.study.springbootdemo.common.Result;
import com.study.springbootdemo.dto.UserDTO;
import com.study.springbootdemo.service.UserService;
import com.study.springbootdemo.vo.LoginVo;
import com.study.springbootdemo.vo.UserVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Tag(name = "用户管理", description = "用户管理接口") //swagger注解，描述接口信息
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/auth/register")
    @Operation(summary = "注册用户", description = "注册一个用户")
    public Result<UserVo> register(@Valid @RequestBody UserDTO userDTO){
        // System.out.println("register注册一个用户"+userDTO);
        logger.info("register注册一个用户:{}", userDTO.getUsername());
        UserVo user =  userService.userRegister(userDTO);
       return Result.success(user);
    }

    @PostMapping("/auth/login")
    @Operation(summary = "登录", description = "登录一个用户") //swagger注解，描述接口信息
    public Result<LoginVo> login(@Valid @RequestBody UserDTO userDTO){
        logger.info("登录用户:{}", userDTO.getUsername());
        LoginVo loginVo  =  userService.userLogin(userDTO);
        return Result.success(loginVo);
    }
}
