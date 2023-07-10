package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.Demo1Application;
import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.mapper.primary.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2023/2/8 11:16
 * @Description: 修改用户的头像、密码等
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class UserChanger {

    private final String password = "eb21b0937a89e321925a83dd6ed94fac";
    private final String salt = "2b184";

    @Autowired
    private UserMapper2 userMapper2;
    @Test
    public void changePassword() {
       //修改用户的账号和密码
        List<User>users =  userMapper2.getAllUser();
        for (User user:users) {
            int id = user.getId();
            userMapper2.updatePassword(id, password, salt);
        }
    }
}
