package com.study.springbootdemo.service;

import com.study.springbootdemo.dto.BookDTO;
import com.study.springbootdemo.vo.BookVo;
import java.util.List;
import com.study.springbootdemo.dto.BookUpdateDto;
import com.study.springbootdemo.common.PageResult;

public interface BookService {

    public String getBookName();
    public BookVo addBook(BookDTO book);
    public BookVo getBookInfo(Long id);
    public String deleteBook(Long id);
    public BookVo updateBook(Long id, BookDTO book);
    public List<BookVo> getAllBooks();
    public List<BookVo> getBooksByCondition(String name, String author);
    public BookVo updateBookSet(Long id, BookUpdateDto bookUpdateDto);
    public PageResult<BookVo> getBooksByConditionWithPage(String name, String author, int page, int pageSize);
}
