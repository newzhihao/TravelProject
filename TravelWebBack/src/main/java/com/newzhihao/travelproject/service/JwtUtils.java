package com.newzhihao.travelproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，用于生成和解析JWT令牌
 */
public class JwtUtils {
    
    // 签名密钥，实际应用中应放在配置文件中
    private static final String SECRET_KEY = "aXRjYXN0";
    
    // 过期时间：6小时（单位：毫秒）
    private static final long EXPIRATION_TIME = 6 * 60 * 60 * 1000;
    
    /**
     * 生成JWT令牌
     * @param claims 要包含在令牌中的自定义声明
     * @return 生成的JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        // 计算过期时间
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        
        // 构建并返回JWT令牌
        return Jwts.builder()
                .setClaims(claims)  // 设置自定义声明
                .setExpiration(expirationDate)  // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 设置签名算法和密钥
                .compact();
    }
    
    /**
     * 解析JWT令牌
     * @param token 要解析的JWT令牌字符串
     * @return 解析后的声明对象
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)  // 指定签名密钥
                .parseClaimsJws(token)  // 解析令牌
                .getBody();  // 获取声明体
    }

}