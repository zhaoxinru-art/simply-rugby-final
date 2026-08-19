package com.sqa.simplyrugby.service;

import com.sqa.simplyrugby.vo.LoginRequest;

public interface LoginService {
    String login(LoginRequest request);
}