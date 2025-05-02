package com.flood_web.security.controller;

import com.flood_web.controller.Administrators;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/outside")
public class SecurityController {

    private static final String VIEW_PATH = "/model/outside";

    @GetMapping("/login")
    public ModelAndView getAdministrators(){
        return new ModelAndView(VIEW_PATH  + "/login");
    }

    @GetMapping("/dologin")
    public String login() {
        return "login";
    }
}
