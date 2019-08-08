package com.mashangshouche.car.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Optional;

public class JwtHelper {

    private final static String USER_ID = "userId";
    private Algorithm algorithm;
    private JWTVerifier verifier;

    public JwtHelper(String secret) throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    public String sign(String userId) {
        return JWT.create().withClaim(USER_ID, userId).sign(algorithm);
    }

    public String sign(String userId, long expires) {
        Date expiresDate = new Date(System.currentTimeMillis() + expires);
        return JWT.create().withClaim(USER_ID, userId).withExpiresAt(expiresDate).sign(algorithm);
    }

    public Optional<String> verify(String token) {
        DecodedJWT jwt = verifier.verify(token);
        Claim claim = jwt.getClaim(USER_ID);
        return Optional.ofNullable(claim.asString());
    }


}
