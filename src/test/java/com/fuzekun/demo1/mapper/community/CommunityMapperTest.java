package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.Demo1Application;
import com.fuzekun.demo1.entity.community.Comment;
import com.fuzekun.demo1.entity.community.Message;
import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.entity.community.DiscussPost;
import com.fuzekun.demo1.exception.MessagingException;
import com.fuzekun.demo1.service.CommentService;
import com.fuzekun.demo1.utils.CommunityConstant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 19:52
 * @Description:
 *
 *
 * 很明显的一个问题，是不是SQL语句出现了错误？或者返回的类型不对？
 *
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class CommunityMapperTest implements CommunityConstant {

    @Autowired
    DiscussPostMapper mapper;

    @Autowired
    UserMapper2 userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    MessageMapper messageMapper;


    @Test
    public void userTest() {
        User user = userMapper.selectById(149);
        System.out.println(user);

//        List<User> users = userMapper.getAllUser();
//        for (User user : users) {
//            System.out.println(user);
//        }
    }

    @Test
    public void test() {

// 创建时间和userid有问题，一个是Null,一个是0,为什么转化会出现错误呢？

        // 因为@Data注解出现了问题

        List<DiscussPost> lst = mapper.selectDiscussPosts(101, 0, 10, 1);
        for (DiscussPost x : lst) {
            System.out.println(x);
        }
        System.out.println(lst.size());
//        DiscussPost d = mapper.findById(1);
        int rows = mapper.selectDiscussPostRows(101);
        System.out.println(rows);

        DiscussPost one = mapper.selectDiscussPostById(112);
        System.out.println(one);
//        List<Map<String, Object>>discussPosts = new ArrayList<>();
//        if (lst != null) {
//            for (DiscussPost post: lst) {
//                Map<String, Object> mp = new HashMap<>();
//                mp.put("post", post);
//                User user = userMapper.findById(0);
//                mp.put("user", user);
//                System.out.println(user + " " + post.getUserId());
//                discussPosts.add(mp);
//            }
//        }

//        User user = userMapper.findById(1);
//        System.out.println(user);
    }
    @Test
    @Deprecated                             // 已经修改过了，不用修改了。
    public void changeHeadUrl() {
        /*
        * 把用户的头像都修改成https协议的，要不然的话浏览器不显示
        * 更新一次就行了，在更新就错误了
        * */
        List<User>list = userMapper.getAllUser();
        int n = list.size();
        System.out.println(n);
//        for (int i = 0; i < n; i++) {
//            User user = list.get(i);
//            // 用户可能没有头像。
//            if (user.getHeader_url() == null) continue;
//            String s = user.getHeader_url();
//            String ns = s.substring(0, 4) + "s" + s.substring(4);
////            user.setHeader_url(ns);
////            System.out.println(user);
//            userMapper.updataHeadUrl(user.getId(), ns);
//        }
    }

    @Test
    public void testComment() {
        Comment comment = commentMapper.findCommentById(1);
        Date date = comment.getCreateTime();
        SimpleDateFormat dateFormatformat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String time = dateFormatformat.format(date);
        System.out.println(time);
        System.out.println(comment);
    }

    @Test
    public void testMessage() {
        // 测试通知
        List<Message>messages = messageMapper.selectNotices(11, TOPIC_COMMENT, 0,5);
        int num1 = messageMapper.selectNoticeUnreadCount(11, TOPIC_COMMENT);
        int num2 = messageMapper.selectNoticeCount(11, TOPIC_COMMENT);
        Message message = messageMapper.selectLatestNotice(11, TOPIC_COMMENT);
        System.out.println(num1);
        System.out.println(num2);
        System.out.println(message);
    }

}
