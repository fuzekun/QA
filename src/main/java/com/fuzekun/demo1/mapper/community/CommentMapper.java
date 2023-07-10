package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.entity.community.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findCommentById(int id);

}
