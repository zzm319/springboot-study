package com.study;

import com.study.service.BookService;
import com.study.service.impl.BookServiceImpl;

public class App {
    public static void main(String[] args ) {
        BookService bookService = new BookServiceImpl();
        bookService.save();
    }
}
