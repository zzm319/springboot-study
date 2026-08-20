package com.study.springbootdemo.vo;

import java.time.LocalDateTime;

public class UserVo {
    private int id;
    private String username;
    private LocalDateTime createTime;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
