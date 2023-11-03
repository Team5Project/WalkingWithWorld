package com.team5.WalkingWithWorld.users.controller;

import com.team5.WalkingWithWorld.users.dto.LoginDto;
import com.team5.WalkingWithWorld.users.dto.RequestUsersDTO;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.global.Login;
import com.team5.WalkingWithWorld.global.SessionConst;
import com.team5.WalkingWithWorld.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/login")
    public String loginIndex(@RequestParam(defaultValue = "/") String redirectURL,
                             Model model,
                             HttpSession session,
                             HttpServletRequest request) {
        model.addAttribute("redirectURL", redirectURL);

        System.out.println(request.getHeader("Referer"));

        return "login_Form";
    }

    @GetMapping("/logout")
    public String logoutIndex(@Login UsersDTO usersDto,
                              HttpSession session) {
        if (usersDto != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginDto loginDto,
                            HttpSession session,
                            BindingResult bindingResult,
                            HttpServletRequest requet
    ) {

        if (bindingResult.hasErrors()) {
            return "login_Form";
        }

        UsersDTO user = userService.getUserInfo(loginDto);
        if (user == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            System.out.println("아이디 또는 패스워드 오류");
            return "login_Form";
        }

        session.setAttribute(SessionConst.LONGIN_USERS, user);
        String redirectURL = requet.getParameter("redirectURL");

        if (redirectURL != null && !redirectURL.equals("/logout")) {
            System.out.println("리다이렉트 확인" + redirectURL);
            return "redirect:" + redirectURL;
        }
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String index(HttpServletRequest request,
                        Model model){
        String referer = request.getHeader("Referer");
        model.addAttribute("referer", referer);
        return "signup_Form";
    }

    @PostMapping("/signup")
    public String signUpUser(RequestUsersDTO usersDto) {
        userService.createUser(usersDto);
        return "login_Form";
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity findAll(){
        List<UsersDTO> userList =userService.getAllUsers();
        return new ResponseEntity(userList, HttpStatus.OK);
    }

    @GetMapping("/users/{users-id}")
    @ResponseBody
    public ResponseEntity findAll(@PathVariable("users-id") int id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

}
