package com.fuzekun.demo1.entity.community;

import lombok.Data;

import java.util.Date;

@Data
public class LoginTicket {

  private long id;
  private int userId;
  private String ticket;
  private long status;
  private Date expired;

//
//  public long getId() {
//    return id;
//  }
//
//  public void setId(long id) {
//    this.id = id;
//  }
//
//
//  public long getUserId() {
//    return userId;
//  }
//
//  public void setUserId(long userId) {
//    this.userId = userId;
//  }
//
//
//  public String getTicket() {
//    return ticket;
//  }
//
//  public void setTicket(String ticket) {
//    this.ticket = ticket;
//  }
//
//
//  public long getStatus() {
//    return status;
//  }
//
//  public void setStatus(long status) {
//    this.status = status;
//  }
//
//
//  public java.sql.Timestamp getExpired() {
//    return expired;
//  }
//
//  public void setExpired(java.sql.Timestamp expired) {
//    this.expired = expired;
//  }

}
