package com.study.springbootdemo.service.impl;

import com.study.springbootdemo.dto.BookDTO;
import com.study.springbootdemo.service.BookService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.study.springbootdemo.vo.BookVo;
import java.util.List;
import com.study.springbootdemo.dto.BookUpdateDto;
import com.study.springbootdemo.common.PageResult;
@Primary
@Service
public class BookServiceImpl implements BookService {
    @Override
    public String getBookName() {
        return "Java编程思想";
    }

    @Override
    public BookVo addBook(BookDTO book){
        return null;
    }

    @Override
    public BookVo getBookInfo(Long id) {
        return null;
    }

    @Override
    public String deleteBook(Long id) {
        return "书籍ID" + id + "删除成功";
    }

    @Override
    public BookVo updateBook(Long id, BookDTO bookDto) {
        return null;
    }
    @Override
    public List<BookVo> getAllBooks() {
        return null;
    }

    @Override
    public List<BookVo> getBooksByCondition(String name, String author) {
        return null;
    }

    @Override
    public BookVo updateBookSet(Long id, BookUpdateDto bookUpdateDto) {
        return null;
    }

    @Override
    public PageResult<BookVo> getBooksByConditionWithPage(String name, String author, int page, int pageSize) {
        return null;
    }
}
