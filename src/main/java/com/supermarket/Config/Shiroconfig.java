package com.supermarket.Config;



import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class Shiroconfig {

//    @Bean(name = "shiroDialect")
//    public ShiroDialect shiroDialect(){
//        return  new ShiroDialect();
//    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(myshiroRealm());
        return securityManager;
    }
    @Bean
    public MyshiroRealm myshiroRealm(){
        MyshiroRealm myshiroRealm=new MyshiroRealm();
        return myshiroRealm;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("3:ShiroConfiguration.shiroFilter()：配置权限控制规则");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/");
//    // 登录成功后要跳转的链接
//    shiroFilterFactoryBean.setSuccessUrl("/main");
        //设置无权限时跳转的 url;
        //如何测试？ 通过filterChainDefinitionMap.put("/user/list", "perms[用户管理]");不允许访问，自动调到403方法返回页面。
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

//    //拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //【开放=anon】登陆接口  这个一定要开放，否则就登录不了。
            filterChainDefinitionMap.put("/devlogin", "anon");
        // 如何测试，打开谷歌最好先清空数据和缓存；测试登陆页面，加上下面这段话代表登陆页面【不拦截】资源显示；如果去除，登陆页面资源部显示；
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/localcss/**", "anon");

//    //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        ////对应页面<a th:href="@{/logout}" href="/logout"><i>class="fa fa-sign-out pull-right"></i>退出</a>
        filterChainDefinitionMap.put("/logout", "logout");

//    //【拒绝访问】配置页面不能访问的路径
        //   key=/user/list     必须和<a href="/user/list">用户管理</a>你所访问的路径一致；value=perms[用户管理]  名字不要求一致，但是不能为空~
//    filterChainDefinitionMap.put("/user/list", "perms[用户管理]");
//    filterChainDefinitionMap.put("/user/add", "perms[用户添加]");
        filterChainDefinitionMap.put("/user/list", "perms[角色管理]");
//
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/thoriz");
        return shiroFilterFactoryBean;

    }

}
