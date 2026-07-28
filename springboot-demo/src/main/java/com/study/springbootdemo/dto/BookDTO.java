package com.study.springbootdemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookDTO {
    private Long id; 

    @NotBlank(message = "书名不能为空")
    @Size(min = 2, max = 100, message = "书名长度必须在2到100之间")
    private String name; 
     
    @NotBlank(message = "作者不能为空")
    private String author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
