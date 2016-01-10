package com.project.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by rohitgupta on 12/14/15.
 */

@Controller
public class LoginController {

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

}
