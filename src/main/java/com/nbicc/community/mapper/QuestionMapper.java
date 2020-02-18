package com.nbicc.community.mapper;

import com.nbicc.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void insertQuestion(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    public List<Question> getList(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    public Integer getCount();

    @Select("select count(1) from question where creator = #{id}")
    public Integer getCountById(@Param("id") Integer id);

    @Select("select * from question where creator=#{id} limit #{offset}, #{size}")
    public List<Question> getListById(@Param("id") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(@Param("id") Integer id);

    @Update("update question set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tag=#{tag} where id=#{id}")
    void updateQuestion(Question question);

    @Update("update question set view_count=view_count + 1 where id=#{id}")
    void incVeiwCount(Integer id);
}
