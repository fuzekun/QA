package com.fuzekun.demo1.entity;

import lombok.Data;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 16:08
 * @Description:
 */
@Data
public class Book {
    private int id;
    private String name;
    private int state;
    private String author;
    private String price;
}
