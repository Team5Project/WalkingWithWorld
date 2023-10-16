package com.team5.WalkingWithWorld.controller;

import com.team5.WalkingWithWorld.domain.LoginDto;
import com.team5.WalkingWithWorld.domain.UsersDto;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.global.SessionConst;
import com.team5.WalkingWithWorld.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String index(){
        return "signupForm";
    }

    @GetMapping("/login")
    public String loginIndex(@RequestParam(defaultValue = "/") String redirectURL,
                             Model model,
                             HttpSession session) {
        model.addAttribute("redirectURL", redirectURL);

        return "loginForm";
    }

    @GetMapping("/logout")
    public String logoutIndex(@Login UsersDto usersDto,
                              HttpSession session) {
        if (usersDto != null) {
            session.invalidate();
        }
        return "";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginDto loginDto,
                            HttpSession session,
                            BindingResult bindingResult,
                            HttpServletRequest requet
    ) {

        if (bindingResult.hasErrors()) {
            return "loginForm";
        }

        UsersDto user = userService.getUserInfo(loginDto);
        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            System.out.println("아이디 또는 패스워드 오류");
            return "loginForm";
        }

        session.setAttribute(SessionConst.LONGIN_USERS, user);
        String redirectURL = requet.getParameter("redirectURL");

        if (redirectURL != null && !redirectURL.equals("/logout")) {
            System.out.println("리다이렉트 확인" + redirectURL);
            return "redirect:" + redirectURL;
        }
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signUpUser(UsersDto usersDto) {
        List<UsersDto> userList = userService.getAllUsers();
        boolean user = userService.createUser(usersDto);
        System.out.println(user);
        return "loginForm";
    }

}
