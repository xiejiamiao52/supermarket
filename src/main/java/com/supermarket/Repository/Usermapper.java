package com.supermarket.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.supermarket.Pojo.User;
import org.apache.ibatis.annotations.Param;

public interface Usermapper extends BaseMapper<User> {
    public User login(@Param("name")String name,@Param("password")String password);

    public User getuser(@Param("name")String name);
}
