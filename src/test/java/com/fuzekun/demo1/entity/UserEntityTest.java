package com.fuzekun.demo1.entity;


import org.junit.Test;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 9:53
 * @Description:
 */

// 首先测试一下@Data是否可以使用User的构造和get, set方法
public class UserEntityTest {

//    @Test
    public void testCon() {
        User user = new User();         // 没有全参构造器吗？只有无参构造器
        user.setActiveCode("fuzekunDADA");
        System.out.println(user.getActiveCode());
    }

//    @Test
    public void testPath() {
        System.out.println(System.getProperty("user.dir"));
    }
}
