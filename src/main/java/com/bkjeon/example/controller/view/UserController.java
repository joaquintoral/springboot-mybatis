package com.bkjeon.example.controller.view;

import com.bkjeon.example.domain.user.UserPrincipal;
import com.bkjeon.example.entity.user.User;
import com.bkjeon.example.service.UserService;
import javax.validation.Valid;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Log4j2
public class UserController {
    final String className = this.getClass().getSimpleName();

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/", "login"})
    public ModelAndView getLoginPage() {
        log.info("MCI > " + className + " -> getLoginPage()");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        log.info("MCO > " + className + " -> getLoginPage()");
        return modelAndView;
    }

    @GetMapping("registration")
    public ModelAndView getRegistrationPage() {
        log.info("MCI > " + className + " -> getRegistrationPage()");
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/registration");
        log.info("MCO > " + className + " -> getRegistrationPage()");
        return modelAndView;
    }

    @PostMapping("registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        log.info("MCI > " + className + " -> createNewUser()");
        ModelAndView modelAndView = new ModelAndView();

        User userExists = userService.findUserByLoginId(user.getLoginId());
        if (userExists != null) {
            bindingResult
                .rejectValue("loginId", "error.loginId",
                    "There is already a user registered with the loginId provided");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("user/registration");
        }

        log.info("MCO > " + className + " -> createNewUser()");
        return modelAndView;
    }

    @GetMapping("home")
    public ModelAndView home(){
        log.info("MCI > " + className + " -> home()");
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        System.out.println(userPrincipal.toString());

        modelAndView.addObject("userName", "Welcome " + userPrincipal.getName() + " (" + userPrincipal.getId() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("user/home");
        log.info("MCO > " + className + " -> home()");
        return modelAndView;
    }

    @GetMapping("exception")
    public ModelAndView getUserPermissionExceptionPage() {
        log.info("MCI > " + className + " -> getUserPermissionExceptionPage()");
        ModelAndView mv = new ModelAndView();

        mv.setViewName("user/access-denied");

        log.info("MCO > " + className + " -> getUserPermissionExceptionPage()");
        return mv;
    }

}