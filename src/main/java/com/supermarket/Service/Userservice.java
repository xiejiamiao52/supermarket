package com.supermarket.Service;

import com.supermarket.Pojo.User;
import org.apache.ibatis.annotations.Param;

public interface Userservice {
    public User login(String name, String password);

    public User getuser(String name);
}
