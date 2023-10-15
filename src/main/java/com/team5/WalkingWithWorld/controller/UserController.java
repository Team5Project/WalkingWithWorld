package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.dao.UserMapper;
import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.Users;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(LoginDto loginDto,
                                  HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Users user = userService.getUserInfo(loginDto);
        int auth =user.getId();

        System.out.println("로그인 성공");

        session.setAttribute("Authorization",auth);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String index(){
        return "signup";
    }

    @PostMapping("/signup")
    public ModelAndView signUpUser(UsersDto usersDto){
        ModelAndView modelAndView = new ModelAndView();
        List<UsersDto> userList = userService.getAllUsers();
        userService.createUser(usersDto);

        modelAndView.addObject("list", userList);
        modelAndView.setViewName("login");
        return modelAndView;
    }

}
