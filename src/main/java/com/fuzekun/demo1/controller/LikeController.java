package com.fuzekun.demo1.controller;

import com.fuzekun.demo1.entity.community.Event;
import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.event.EventProducer;
import com.fuzekun.demo1.service.LikeService;
import com.fuzekun.demo1.service.UserService;
import com.fuzekun.demo1.utils.CommunityConstant;
import com.fuzekun.demo1.utils.CommunityUtil;
import com.fuzekun.demo1.utils.HostHolder;
import com.fuzekun.demo1.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId){
        User user = hostHolder.getUser();
        // 这里是经过测试使用的，如果没有也可以点赞，算到管理员头上，或者加上一个游客用户，算到游客头上。
//        if (user == null) user = userService.findById(1);
        // 或者在service里面加上逻辑，如果user为空，无权点赞,这个可以使用拦截器进行实现。

//        System.out.println("进入");
        // 点赞
        likeService.like(user.getId(), entityType, entityId, entityUserId);

        // 数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        // 状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        // 返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("likeStatus", likeStatus);

        // 触发点赞事件, 取消点赞不需要, 这样点赞可以点多个，但是一个用户只是提供一个通知
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);   // 这里的entityId说白了就是对应的PostId
            eventProducer.fireEvent(event);
        }
        String redisKey = RedisKeyUtil.getPostScoreKey();
        redisTemplate.opsForSet().add(redisKey, postId);
        return CommunityUtil.getJSONString(0, null, map);
    }

}
