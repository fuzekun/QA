package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.entity.community.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findCommentById(int id);

    @Select("select count(*) from comment where user_id = #{userId}")
    int queryUserAllComment(int userId);

    @Select("select * from comment where user_id = #{userId} limit #{offset}, #{limit}")
    List<Comment> queryUserAllCommentList(int userId, int offset, int limit);

}
