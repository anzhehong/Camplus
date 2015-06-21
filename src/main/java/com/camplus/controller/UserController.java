package com.camplus.controller;

import com.camplus.entity.User;
import com.camplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fowafolo on 15/5/12.
 */


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

//    @RequestMapping("/login")
//    public String login(String userId,String userPassword,Model user,HttpSession session){
//        int flag = userService.checkIdentity(userId,userPassword);
//        if (flag==0){
//            user.addAttribute("message","用户名不存在！");
//            return "loginFail";
//        }else if (flag==1){
//            user.addAttribute("message","密码错误");
//            return "loginFail";
//        }else {
//            user.addAttribute("message","登录成功！");
//            String userName = userService.getById(userId).getUserName();
//            user.addAttribute("userName",userName);
//            session.setAttribute("userSession",userService.getById(userId));
//            return "index";
//        }
//    }


    @RequestMapping("/checkUserPassword")
    @ResponseBody
    public boolean checkUserPassword(String userId,String userPassword){
        User user = userService.getById(userId);
        System.out.println(user);
        if(user.getUserPassword().equals(userPassword)){
            return true;
        }else{
            return false;
        }
    }


    //    @RequestMapping("/register")
//    public String register(String userName,String userId,String userPassword,Model userRegister){
//        User user = new User();
//        user.setUserName(userName);
//        user.setUserPassword(userPassword);
//        user.setUserId(userId);
//        user.setUserExperience(0);
//        user.setUserLevel(1);
//        userRegister.addAttribute("userName",userName);
//        userRegister.addAttribute("userId",userId);
//        userService.userRegister(user);
//
//        return "login";
//    }
    @RequestMapping("/checkUserIdExist")
    @ResponseBody
    public boolean check(String userId){
        User user = userService.getById(userId);
        if(user != null){
            return true;
        }else{
            return false;
        }
    }

    @RequestMapping("/editInfo")
    public String editInfo() {
        return "selfInfo";
    }
}
