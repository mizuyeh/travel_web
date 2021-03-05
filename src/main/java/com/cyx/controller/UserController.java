package com.cyx.controller;

import com.cyx.entity.User;
import com.cyx.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description
 * @date 2021/3/3
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login.do")
    public String login(User user, Model model) {
        //获得当前用户到登录对象，现在状态为未认证
        Subject subject= SecurityUtils.getSubject();
        //用户名密码令牌
        AuthenticationToken token=new UsernamePasswordToken(user.getUsername(),user.getPassword());
        //shiro 使用异常捕捉登录失败消息
        try {
            //将令牌传到shiro提供的login方法验证，需要自定义realm
            subject.login(token);
            //没有异常表示验证成功
            return "redirect:/index.jsp";
        } catch (IncorrectCredentialsException ice){
            model.addAttribute("loginInfo","用户名或密码不正确！");
        }catch(UnknownAccountException uae){
            model.addAttribute("loginInfo","未知账户！");
        }catch(LockedAccountException lae){
            model.addAttribute("loginInfo",lae.getMessage());
        }catch(ExcessiveAttemptsException eae){
            model.addAttribute("loginInfo","用户名或密码错误次数太多！");
        }catch(AuthenticationException ae){
            ae.printStackTrace();
            model.addAttribute("loginInfo","验证未通过！");
        }catch (Exception e) {
            model.addAttribute("loginInfo","验证未通过！");
        }
        return "login-fail";
    }

    @RequestMapping("/logout.do")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }

    @RequestMapping("/register.do")
    public String register(User user, Model model) {
        User byUsername = userService.findByUsername(user.getUsername());
        if(byUsername != null) {
            model.addAttribute("error", "此用户已存在!");
            model.addAttribute(user);
            return "forward:/register.jsp";
        }
        userService.register(user);
        return "redirect:/login.jsp";
    }

    @RequiresPermissions("user:view")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        List<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userList", users);
        modelAndView.setViewName("user-list");
        return modelAndView;
    }
}
