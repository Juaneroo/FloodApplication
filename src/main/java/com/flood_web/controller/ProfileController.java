package com.flood_web.controller;

import com.flood_web.service.crud.AlertCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/inside")
public class ProfileController {

    private static final String VIEW_PATH = "/model/inside";

    @GetMapping("/profile")
    public ModelAndView profile() {
        return new ModelAndView(VIEW_PATH + "/profile"); //
    }
}