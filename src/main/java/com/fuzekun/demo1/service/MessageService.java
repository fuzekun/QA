package com.fuzekun.demo1.service;

import com.fuzekun.demo1.entity.community.Message;
import com.fuzekun.demo1.mapper.community.MessageMapper;
import com.fuzekun.demo1.utils.SensitiveFilter;
import org.apache.kafka.common.metrics.Measurable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    public int readMessage(List<Integer> ids) {
//        System.out.println("修改完成");
        return messageMapper.updateStatus(ids, 1);
    }

    public Message findLatestNotice(int userId, String topic) {
        return messageMapper.selectLatestNotice(userId, topic);
    }

    public int findNoticeCount(int id, String topicComment) {
        return messageMapper.selectNoticeCount(id, topicComment);
    }

    public int findNoticeUnreadCount(int id, String topicComment) {
        return messageMapper.selectNoticeUnreadCount(id, topicComment);
    }

    public List<Message> findNotices(int id, String topic, int offset, int limit) {
        return messageMapper.selectNotices(id, topic, offset, limit);
    }
}
