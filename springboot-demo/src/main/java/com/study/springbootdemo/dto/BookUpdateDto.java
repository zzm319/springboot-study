package com.study.springbootdemo.dto;

public class BookUpdateDto {
    public String name;
    public String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
