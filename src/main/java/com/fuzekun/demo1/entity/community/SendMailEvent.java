package com.fuzekun.demo1.entity.community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2023/7/13 17:43
 * @Description: 发送邮件事件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMailEvent {
    private String topic;
    private String userMail;
    private String htmlPath;
    private Map<String, Object>htmlV ;

}
