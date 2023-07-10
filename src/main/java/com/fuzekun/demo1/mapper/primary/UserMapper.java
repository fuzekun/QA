package com.fuzekun.demo1.mapper.primary;

import com.fuzekun.demo1.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 9:58
 * @Description:
 */


@Repository
public interface UserMapper {

    public User findById(int id);
}
