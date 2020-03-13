package com.supermarket.Controller;

import com.supermarket.Pojo.User;
import com.supermarket.Service.Userservice;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class Usercontroller {
    @Resource
    private Userservice userservice;
    @RequestMapping(value = "/register")
    public String login(){
        return "devlogin";
    }

    @RequestMapping(value = "/dologin")
    public String dologin(@RequestParam("devCode")String devCode, @RequestParam("devPassword")String devPassword, Model model, HttpSession session
                          ) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(devCode, devPassword);
        try {
            subject.login(token);
             User user= (User) SecurityUtils.getSubject().getPrincipal();
             session.setAttribute("devUserSession",user);
            return "developer/main";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名错误");
            return "devlogin";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            return "devlogin";
        }catch (DisabledAccountException e){
            model.addAttribute("msg","由于输入错误次数大于5，账户1小时内禁止登录");
            return "devlogin";
        }

    }

    @RequestMapping(value = "/list")
    public String appinfolist(){
        return "developer/appinfolist";
    }
    @RequestMapping("/thoriz")
    @ResponseBody
    public String nothriz(){
       return  "未授权页面";
    }
}
