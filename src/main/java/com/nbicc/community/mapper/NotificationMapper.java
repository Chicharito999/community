package com.nbicc.community.mapper;

import com.nbicc.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification(notifier,receiver,outer_id,type,gmt_create,status) values(#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status})")
    void insert(Notification notification);

    @Select("select count(1) from notification where receiver=#{receiver} and status=0")
    Integer readCount(@Param("receiver") long receiver);

    @Select("select * from notification where receiver=#{id} and status=0 order by gmt_create desc limit #{offset}, #{size}")
    List<Notification> getListById(@Param("id") Long id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from notification where id=#{id}")
    Notification getOneById(@Param("id") Long id);

    @Update("update notification set status=1 where id=#{id}")
    void updateById(@Param("id") Long id);
}
