package com.tlg.bkwb.controller;

import com.tlg.bkwb.bean.User;
import com.tlg.bkwb.service.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author tlg
 * @create 2021-01-07 2:18
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录页
     * @return
     */
    @RequestMapping("/login.html")
    public String loginPage(){
        return "login.html";
    }

    /**
     * 获取登录用户
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLoginUser")
    public String getLoginUser(HttpSession session){
        String username = (String) session.getAttribute("loginUsername");
        return username;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam(value = "username",defaultValue = "") String username, @RequestParam(value = "password",defaultValue = "") String password,
                        Map<String, Object> map, HttpSession session){
        User user = userService.login(username, password);
        if (user != null){
            //登录成功
            System.out.println(user);
            session.setAttribute("loginId",user.getUserId());
            session.setAttribute("loginUser",user.getNickname());
            session.setAttribute("loginUsername",user.getUsername());
            return "redirect:/index.html";
        } else {
            //登录失败
            map.put("msg","用户名或密码错误");
            return "/login.html";
        }
    }

    /**
     * 注册页
     * @return
     */
    @RequestMapping("/register.html")
    public String RegisterPage() {
        return "register";
    }

    /**
     * 注册用户
     * @param user
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/register")
    public String register(User user, Map<String, Object> map, HttpSession session) {
        String username = user.getUsername();
        // 查询用户名是否被占用
        boolean exists = userService.exists(username);
        if (exists) {
            map.put("msg","用户名已经被注册!");
            return "register";
        }
        boolean register = userService.register(user);

        if (!register){
            map.put("msg","未知错误!");
            return "register";
        }

        log.info("new register  ===============  " + user);
        session.setAttribute("loginId",user.getUserId());
        session.setAttribute("loginUser",user.getNickname());
        session.setAttribute("loginUsername",user.getUsername());
        return "redirect:/index.html";
    }


    /**
     * 注销用户
     * @param session
     * @return
     */
    @RequestMapping("/user/exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    /**
     * 获取余额
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/user/getBalance")
    public String getBalance(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("loginId");
        return userService.getBalance(userId).toString();
    }
}
