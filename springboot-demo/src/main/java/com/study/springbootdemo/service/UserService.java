package com.study.springbootdemo.service;

import com.study.springbootdemo.dto.UserDTO;
import com.study.springbootdemo.vo.LoginVo;
import com.study.springbootdemo.vo.UserVo;



public interface UserService {
    public UserVo userRegister(UserDTO userDTO);
    public LoginVo userLogin(UserDTO userDTO);
}
