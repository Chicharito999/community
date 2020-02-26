package com.nbicc.community.mapper;

import com.nbicc.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,gmt_create,gmt_modified,like_count,comment_count,content) values(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{commentCount},#{content})")
    void insert(Comment comment);

    @Select("select * from comment where id = #{id}")
    Comment findById(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{id} order by gmt_create desc")
    List<Comment> findByParentId(@Param("id") long longValue);
}
