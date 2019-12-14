package com.snake.mcf.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

/**
 * @ClassName JwtUtil
 * @Author 大帅
 * @Date 2019/6/22 19:01
 */
public class JwtUtil {

    /**
     * 生成加密后的token
     * @param username 用户名
     * @param name  姓名
     * @return 加密后的token
     */
    public static String getToken(final String username,
                           final String name) {
        return getToken(false,username,name);
    }

    /**
     * 生成加密后的token
     * @param isVip 是不是VIP,true表示是VIP，false表示不是VIP。
     * @param username 用户名
     * @param name  姓名
     * @return 加密后的token
     */
    public static String getToken(final boolean isVip, final String username,
                           final String name) {
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("isVip", isVip)
                    .withClaim("username", username)
                    .withClaim("name", name)
                    .withExpiresAt(expiresAt)
                    // 使用了HMAC256加密算法。
                    // mysecret是用来加密数字签名的密钥。
                    .sign(Algorithm.HMAC256("mysecret"));
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 先验证token是否被伪造，然后解码token。
     * @param token 字符串token
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    public static DecodedJWT deToken(final String token) {
        DecodedJWT jwt = null;
        try {
            // 使用了HMAC256加密算法。
            // mysecret是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mysecret"))
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jwt;
    }

    public static void main(String[] args) {
        String admin = getToken(GeneratotUtils.generateBlank2LowerCase(), "admin");
        System.out.println(admin);
    }



}
