package com.fuzekun.demo1.utils;

import com.fuzekun.demo1.Demo1Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;

/**
 * @author: Zekun Fu
 * @date: 2022/10/10 15:04
 * @Description:测试发送邮件的功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class MailUtilTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void test1() {
        mailClient.sendTextMail("welcome", "1078682405@qq.com", "hello mail");
    }

    @Test
    public void testHtmlText() {
        Context context = new Context();
        context.setVariable("username", "fuzekun");
        String content = templateEngine.process("/mail/forget.html", context);
        mailClient.sendTextMail("找回密码", "1078682405@qq.com", content);
    }

    @Test
    public void testHtmlText2() throws Exception {
        HashMap<String, Object>mp = new HashMap<>();
        mp.putIfAbsent("username", "fuzekun");
        String path = "/mail/forget.html";
        mailClient.sendThymeleafMail("找回密码", "1078682405@qq.com", mp, path);
    }
}
