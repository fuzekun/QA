package com.fuzekun.demo1.entity.community;

import lombok.Data;

import java.util.Date;

@Data
public class Message {

  private int id;
  private int fromId;
  private int toId;
  private String conversationId;
  private String content;
  private int status;
  private Date createTime;

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
//  public long getFromId() {
//    return fromId;
//  }
//
//  public void setFromId(long fromId) {
//    this.fromId = fromId;
//  }
//
//
//  public long getToId() {
//    return toId;
//  }
//
//  public void setToId(long toId) {
//    this.toId = toId;
//  }
//
//
//  public String getConversationId() {
//    return conversationId;
//  }
//
//  public void setConversationId(String conversationId) {
//    this.conversationId = conversationId;
//  }
//
//
//  public String getContent() {
//    return content;
//  }
//
//  public void setContent(String content) {
//    this.content = content;
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
//  public java.sql.Timestamp getCreateTime() {
//    return createTime;
//  }
//
//  public void setCreateTime(java.sql.Timestamp createTime) {
//    this.createTime = createTime;
//  }

}
