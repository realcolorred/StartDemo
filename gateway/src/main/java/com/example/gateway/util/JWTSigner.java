package com.example.gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gateway.common.JwtConst;
import com.example.gateway.config.TokenSecretConfig;
import com.example.pubserv.util.SpringBeanUtils;

import java.util.Date;

public class JWTSigner {

    private static JWTVerifier verifier;

    private static Algorithm algorithm;

    static {
        if (verifier == null) {
            synchronized (JWTSigner.class) {
                if (verifier == null) {
                    TokenSecretConfig tokenSecretConfig = SpringBeanUtils.getBean(TokenSecretConfig.class);
                    algorithm = Algorithm.HMAC256(tokenSecretConfig.getTokenSecret());
                    verifier = JWT.require(algorithm).build();
                }
            }
        }
    }

    public static String sign(String payload) {
        return sign(payload, JwtConst.DEFAULT_MAX_AGE, JwtConst.DEFAULT_REFRESH_AGE);
    }

    /**
     * 签名
     * @param payload 签名内容
     * @param maxAge 签名有效时长
     * @param refreshAge 刷新签名的最短间隔
     * @return 1
     */
    public static String sign(String payload, long maxAge, long refreshAge) {
        long currentTime = System.currentTimeMillis();
        JWTCreator.Builder builder = JWT.create().withIssuer(JwtConst.ISSUER).withClaim(JwtConst.USER_INFO, payload);
        if (maxAge >= 0) {
            builder.withExpiresAt(new Date(currentTime + maxAge));
        }
        if (refreshAge >= 0) {
            builder.withClaim(JwtConst.REFRESH, new Date(currentTime + refreshAge));
        }

        return builder.sign(algorithm);
    }

    /**
     * token验证
     * @param token token
     * @return 1
     */
    public static DecodedJWT verify(String token) {
        return verifier.verify(token);
    }

    public static String verifyAndGetUserInfo(String token) {
        return verifier.verify(token).getClaim(JwtConst.USER_INFO).asString();
    }

    /**
     * token更换刷新
     * @param token token
     * @return 1
     */
    public static String maybeExtend(DecodedJWT token) {
        Claim claim = token.getClaim(JwtConst.REFRESH);
        if (claim instanceof NullClaim) {
            return null;
        }
        Date now = new Date();
        if (now.after(claim.asDate())) {
            return sign(token.getClaim(JwtConst.USER_INFO).asString());
        }
        return null;
    }
}
