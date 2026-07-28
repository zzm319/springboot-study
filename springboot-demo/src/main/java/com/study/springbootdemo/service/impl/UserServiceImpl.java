package com.study.springbootdemo.service.impl;

import com.study.springbootdemo.common.BusinessException;
import com.study.springbootdemo.common.ResultCode;
import com.study.springbootdemo.dto.UserDTO;
import com.study.springbootdemo.entity.User;
import com.study.springbootdemo.mapper.UserMapper;
import com.study.springbootdemo.service.UserService;
import com.study.springbootdemo.vo.LoginVo;
import com.study.springbootdemo.vo.UserVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.study.springbootdemo.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserMapper userMapper, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    private UserVo toUserVo(User user){
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setCreateTime(user.getCreateTime());
        return userVo;
    }

    @Override
    public UserVo userRegister(UserDTO userDTO){
        User user = userMapper.selectUserByName(userDTO.getUsername());
        if(user != null){
            throw new BusinessException(ResultCode.BAD_REQUEST,"用户已存在");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user1 = new User(userDTO.getUsername(),encodedPassword);
        userMapper.insertUser(user1);
        User user2 = userMapper.selectUserById(user1.getId());
        logger.info("用户注册成功, username={}, id={}",user2.getUsername(), user2.getId());
       return toUserVo(user2);
    }

    @Override
    public LoginVo userLogin(UserDTO userDTO){
        User user = userMapper.selectUserByName(userDTO.getUsername());
        if(user == null){
            logger.warn("用户不存在,username={}",userDTO.getUsername());
            throw new BusinessException(ResultCode.UNAUTHORIZED,"用户名或密码错误");
        }
        // （不要区分「用户不存在」和「密码错误」，防枚举用户名）
        if(!passwordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            logger.warn("用户名或密码错误,username={}",userDTO.getUsername());
            throw new BusinessException(ResultCode.UNAUTHORIZED,"用户名或密码错误");
        }
        LoginVo loginVo = new LoginVo();
        loginVo.setId(user.getId());
        loginVo.setUsername(user.getUsername());
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        loginVo.setToken(token);
        logger.info("用户登录成功,username={}",loginVo.getUsername());
        return loginVo;
    }

}
