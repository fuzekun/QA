package com.fuzekun.demo1.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 9:50
 * @Description:
 */

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private String telphone;
    private String introduce;
    private String activeCode;
    private int state;
    private String role;
    private Date registTime;




}
