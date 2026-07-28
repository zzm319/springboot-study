package com.study.springbootdemo.mapper;


import com.study.springbootdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public void insertUser(User user);
    public User selectUserById(int id);
    public User selectUserByName(String name);
}
