package com.sqa.simplyrugby.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT Token工具类
 * 解决问题：1. JWT依赖爆红 2. 静态方法调用警告 3. JDK8语法兼容
 */
@Component
public class JwtUtil {
    // JWT密钥（长度≥256位，避免签名异常）
    private static final String SECRET = "simply_rugby_jwt_secret_key_1234567890_abcdefg_hijklmn_opqrst";
    // Token过期时间：1天（86400000毫秒）
    private static final long EXPIRATION = 86400000L;

    // 生成Token（静态方法，直接类名调用）
    public static String createToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setSubject(username)       // 存储用户名作为主体
                .setIssuedAt(now)           // 签发时间
                .setExpiration(expiryDate)  // 过期时间
                .signWith(key)              // 签名
                .compact();
    }

    // 解析Token（供拦截器调用）
    public static Jws<Claims> parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}