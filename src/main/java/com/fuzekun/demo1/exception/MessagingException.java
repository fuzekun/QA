package com.fuzekun.demo1.exception;

/**
 * @author: Zekun Fu
 * @date: 2022/10/10 14:56
 * @Description:
 */
public class MessagingException extends Exception{
    public MessagingException() {
        super("邮件发送错误！");
    }
}
