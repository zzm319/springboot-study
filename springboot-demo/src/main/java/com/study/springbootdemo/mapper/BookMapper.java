package com.study.springbootdemo.mapper;

import com.study.springbootdemo.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//数据库操作接口
@Mapper
public interface BookMapper {
    Book selectById(Long id);

    List<Book> selectAll();

    int insert(Book book);

    int deleteById(Long id);

    int updateById(Book book);

    List<Book> selectByCondition(@Param("name") String name, @Param("author") String author);

    long countByCondition(@Param("name") String name, @Param("author") String author);

    List<Book> selectByConditionWithPage(@Param("name") String name,
            @Param("author") String author,
            @Param("offset") int offset,
            @Param("size") int size);
}
