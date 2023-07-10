package com.fuzekun.demo1.entity.community;


import lombok.Data;

@Data
public class User {

  private int id;
  private String username;
  private String password;
  private String salt;
  private String email;
  private int type;
  private int status;
  private String activationCode;
  private String headerUrl;
  private java.sql.Timestamp createTime;


}
