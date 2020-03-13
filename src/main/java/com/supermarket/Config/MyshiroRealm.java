package com.supermarket.Config;

import com.supermarket.Pojo.User;
import com.supermarket.Service.Userservice;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

public class MyshiroRealm extends AuthorizingRealm {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //用户登录次数计数
    private String SHIRO_LOGIN_COUNT="shiro_login_count";
    //用户是否被锁定
    private  String SHIRO_IS_LOCK="shiro_is_lock";
    @Resource
    private Userservice userservice;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("=======授权");
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        Subject subject=SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        info.addStringPermission("1");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证=====================");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String usrName = token.getUsername();
        String usrPassword = new String(token.getPassword());
        ValueOperations<String,String>operations=stringRedisTemplate.opsForValue();
        operations.increment(SHIRO_LOGIN_COUNT+usrName,1);
        if (Integer.parseInt(operations.get(SHIRO_LOGIN_COUNT+usrName))>5) {
        operations.set(SHIRO_IS_LOCK+usrName,"LOCK");
        stringRedisTemplate.expire(SHIRO_IS_LOCK+usrName,1, TimeUnit.MILLISECONDS);
        }
        if("LOCK".equals(operations.get(SHIRO_IS_LOCK+usrName))){
            throw new DisabledAccountException("由于输入错误次数大于5，账户1小时内禁止登录");
        }
        User user1=userservice.getuser(usrName);
        if (user1==null) {
            return  null;
        } else if (!user1.getUsrPassword().equals(usrPassword)) {
            throw new IncorrectCredentialsException("密码不正确！");
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user1,user1.getUsrPassword(),"");
        operations.set(SHIRO_LOGIN_COUNT+usrName,"0");
        operations.set(SHIRO_IS_LOCK+usrName, "");
        return authenticationInfo;
    }


}
