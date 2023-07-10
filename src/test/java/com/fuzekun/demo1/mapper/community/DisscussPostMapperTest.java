package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.Demo1Application;
import com.fuzekun.demo1.entity.community.DiscussPost;
import com.fuzekun.demo1.service.ElasticsearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author: Zekun Fu
 * @date: 2023/1/31 17:45
 * @Description:
 */



@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class DisscussPostMapperTest {
    @Autowired
    DiscussPostMapper discussPostMapper;

    @Autowired
    ElasticsearchService elasticsearchService;
    @Test
    public void testInser() {
        DiscussPost post = new DiscussPost();
        post.setUserId(1);
        post.setTitle("新的帖子3");
        post.setContent("属于付泽坤的帖子2");
        post.setCreateTime(new Date());
        int x = discussPostMapper.insertDiscussPost(post);
        System.out.println(post.getId());

        // 这里进行事件处理，把新加入的帖子放入到
//        elasticsearchService.saveDiscussPost(post);
    }
}
