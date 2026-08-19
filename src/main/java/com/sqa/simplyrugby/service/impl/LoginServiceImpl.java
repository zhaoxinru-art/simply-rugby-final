package com.sqa.simplyrugby.service.impl;

import com.sqa.simplyrugby.service.LoginService;
import com.sqa.simplyrugby.utils.JwtUtil;
import com.sqa.simplyrugby.vo.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public String login(LoginRequest request) {
        if ("admin".equals(request.getUsername()) && "admin123".equals(request.getPassword())) {
            return JwtUtil.createToken(request.getUsername());
        }
        throw new RuntimeException("用户名或密码错误");
    }
}