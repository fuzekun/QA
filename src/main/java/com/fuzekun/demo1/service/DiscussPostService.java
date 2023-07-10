package com.fuzekun.demo1.service;

import com.fuzekun.demo1.entity.community.DiscussPost;
import com.fuzekun.demo1.mapper.community.DiscussPostMapper;
import com.fuzekun.demo1.utils.CommunityConstant;
import com.fuzekun.demo1.utils.RedisKeyUtil;
import com.fuzekun.demo1.utils.SensitiveFilter;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.lettuce.core.RedisURI;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import com.github.benmanes.caffeine.cache.LoadingCache;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DiscussPostService implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(DiscussPostService.class);

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Value("${caffeine.posts.max-size}")
    private int maxSize;

    @Value("${caffeine.posts.expire-seconds}")
    private int expireSeconds;

    // 帖子列表缓存
    private LoadingCache<String, List<DiscussPost>> postListCache;

    // 帖子总数缓存
    private LoadingCache<Integer, Integer> postRowsCache;

//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    public void initPostCache(int offset, int limit) {
//        // 初始化帖子列表
//        String listKey = RedisKeyUtil.getHotPostCacheKey();
//        // 1. 如果缓存数量大于了最大值，直接清空，重新放入
////        if (redisTemplate.opsForList().size(listKey) + limit > maxSize) redisTemplate.opsForList().
//        logger.debug("load data from database");
//        List<DiscussPost>list = discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
//        redisTemplate.opsForList().rightPushAll(listKey, list);
//    }
//
//    public void initRowCach(int userId) {
//        String key = RedisKeyUtil.getRowCach(userId);
//        logger.debug("load post rows from DB.");
//        int rows =  discussPostMapper.selectDiscussPostRows(userId);
//        redisTemplate.opsForValue().append(key, Integer.toString(rows));
//    }

    @PostConstruct
    public void init() {
        // 初始化帖子列表缓存
        postListCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<String, List<DiscussPost>>() {
                    @Nullable
                    @Override
                    public List<DiscussPost> load(@NonNull String key) throws Exception {
                        if (key == null || key.length() == 0) {
                            throw new IllegalArgumentException("参数错误!");
                        }

                        String[] params = key.split(":");
                        if (params == null || params.length != 2) {
                            throw new IllegalArgumentException("参数错误!");
                        }

                        int offset = Integer.valueOf(params[0]);
                        int limit = Integer.valueOf(params[1]);

                        // 二级缓存: Redis -> mysql

                        logger.debug("load post list from DB.");
                        return discussPostMapper.selectDiscussPosts(0, offset, limit, 1);
                    }
                });
        // 初始化帖子总数缓存
        postRowsCache = Caffeine.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expireSeconds, TimeUnit.SECONDS)
                .build(new CacheLoader<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer load(@NonNull Integer key) throws Exception {
                        logger.debug("load post rows from DB.");
                        return discussPostMapper.selectDiscussPostRows(key);
                    }
                });
    }

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode) {
        // 1. 如果不是热点访问数据库
        if (userId == 0 && orderMode == 1)
            return postListCache.get(offset + ":" + limit);
        logger.debug("load post list from DB.");
        return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
//        if (userId != 0 || orderMode != 1) {
//            logger.debug("load post list from DB.");
//            return discussPostMapper.selectDiscussPosts(userId, offset, limit, orderMode);
//        }
//        // 获取列表
//        String hotPostKey = RedisKeyUtil.getHotPostCacheKey();
//        // 2. 如果不足，访问数据库
//        if (redisTemplate.opsForList().size(hotPostKey) < offset + limit) {
//            logger.debug("load post list from DB");
//            initPostCache(offset, limit);
//        }
//        List<DiscussPost>list = redisTemplate.opsForList().range(hotPostKey, offset, offset + limit - 1);

//        return list;
    }

    public int findDiscussPostRows(int userId) {
        // 1. 如果不满足条件，访问数据库

        if (userId == 0)
            return postRowsCache.get(userId);
        logger.debug("load post rows from DB.");
        return discussPostMapper.selectDiscussPostRows(userId);
//        if (userId != 0) {
//            logger.debug("load post rows from DB.");
//            return discussPostMapper.selectDiscussPostRows(userId);
//        }
//        // 2. 如果没有访问数据库
//        String key = RedisKeyUtil.getRowCach(userId);
//        Integer rows = (Integer) redisTemplate.opsForValue().get(key);
//        if (rows == null) initRowCach(userId);
//        return (Integer)redisTemplate.opsForValue().get(key);
    }
    public int addDiscussPost(DiscussPost post) {
        if (post == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        // 转义HTML标记
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        // 过滤敏感词
        post.setTitle(sensitiveFilter.filter(post.getTitle()));
        post.setContent(sensitiveFilter.filter(post.getContent()));

//        System.out.println("成功进入了service中");
        //
        return discussPostMapper.insertDiscussPost(post);
    }

    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentCount) {
        return discussPostMapper.updateCommentCount(id, commentCount);
    }

    public int updateType(int id, int type) {
        return discussPostMapper.updateType(id, type);
    }

    public int updateStatus(int id, int status) {
        return discussPostMapper.updateStatus(id, status);
    }

    public int updateScore(int id, double score) {
        return discussPostMapper.updateScore(id, score);
    }

}
