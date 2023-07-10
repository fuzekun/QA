package com.fuzekun.demo1.mapper.community.elasticsearch;

import com.fuzekun.demo1.entity.community.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author: Zekun Fu
 * @date: 2023/1/31 16:48
 * @Description:
 */
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {
}
