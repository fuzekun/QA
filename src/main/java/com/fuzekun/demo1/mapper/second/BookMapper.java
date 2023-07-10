package com.fuzekun.demo1.mapper.second;

import com.fuzekun.demo1.entity.Book;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 16:10
 * @Description:
 */
public interface BookMapper {
    public Book findById(int id);
}
