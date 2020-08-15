package com.example.gateway;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gateway.util.JWTSigner;
import org.junit.Test;

public class GatewayApplicationTests {

    @Test
    public void test1() {
        String token = JWTSigner.sign("狗屎吧", 300 * 1000, 100 * 1000);
        System.out.println(token);

        DecodedJWT jwt = JWTSigner.verify(token);
        System.out.println(jwt.getClaim("userInfo").asString());
        System.out.println(jwt.getClaim("exp").asDate());
        System.out.println(jwt.getClaim("refresh").asDate());
    }

}
