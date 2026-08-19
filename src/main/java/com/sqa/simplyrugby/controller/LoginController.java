package com.sqa.simplyrugby.controller;

import com.sqa.simplyrugby.service.LoginService;
import com.sqa.simplyrugby.vo.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println("=====================================");
        System.out.println("✅ 后端收到登录请求！用户名：" + request.getUsername());
        System.out.println("=====================================");
        String token = loginService.login(request);
        return ResponseEntity.ok(token);
    }
}