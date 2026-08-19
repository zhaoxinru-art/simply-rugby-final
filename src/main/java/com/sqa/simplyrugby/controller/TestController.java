package com.sqa.simplyrugby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @PostMapping("/fuck")
    @ResponseBody
    public String hello(String name, String email,String password) {
        return "Hello " + name + ", " + email + ", " + password;
    }

@GetMapping("/fuck1")
@ResponseBody
public String hello1() {
    return "Hello SpringBoot11111";
   }
}