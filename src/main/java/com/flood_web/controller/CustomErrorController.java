package com.flood_web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int code = Integer.parseInt(status.toString());
            return switch (code) {
                case 403 -> "error/403";
                case 404 -> "error/404";
                case 500 -> "error/500";
                default -> "error/default";
            };
        }

        return "error/default";
    }
}

