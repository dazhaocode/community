package com.alan.community.mapper;

import com.alan.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void addUser(User user);
    @Select("select * from user where token=#{token}")
    User selectByToken(@Param("token") String token);

    @Select("select * from user where id=#{creator_id}")
    User selectByUserId(@Param("creator_id") Integer creator_id);
}
