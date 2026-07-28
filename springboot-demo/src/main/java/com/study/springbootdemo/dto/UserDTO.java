package com.study.springbootdemo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public class UserDTO {
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 10, message = "用户名长度必须在3到10之间")
    @Schema(description = "用户名", example = "admin") //swagger注解，描述字段信息
    private  String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码长度必须在6到16之间")
    @Schema(description = "密码", example = "123456")
    private  String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
