package com.sqa.simplyrugby.interceptor; // ✅ 正确包名！！！

import com.sqa.simplyrugby.utils.BaseContext;
import com.sqa.simplyrugby.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行登录接口，解决401
        String uri = request.getRequestURI();
        if (uri.contains("/api/login")) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        try {
            Jws<Claims> claimsJws = JwtUtil.parseToken(token);
            Claims claims = claimsJws.getBody();
            Long userId = Long.valueOf(claims.getSubject());
            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}