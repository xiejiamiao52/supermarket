package com.supermarket.Service.Impl;

import com.supermarket.Pojo.User;
import com.supermarket.Repository.Usermapper;
import com.supermarket.Service.Userservice;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Userserviceimpl implements Userservice {
    @Resource
    private Usermapper usermapper;

    @Override
    @Cacheable(value = "getUser",keyGenerator = "keyGenerator")
    public User login(String name, String password) {
        return usermapper.login(name,password);
    }

    @Override
    @Cacheable(value = "getname",keyGenerator = "keyGenerator")
    public User getuser(String name) {
        return usermapper.getuser(name);
    }
}
