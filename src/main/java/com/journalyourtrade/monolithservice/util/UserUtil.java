package com.journalyourtrade.monolithservice.util;

import org.springframework.http.HttpHeaders;

public class UserUtil {

    public static String getUsernameFromHeaders(HttpHeaders headers, JwtUtil jwtUtil){
        String jwt = getTokenHeaders(headers, jwtUtil);
        String username = null;

        if (jwt != null && jwt.length() > 0) {
            username = jwtUtil.extractUsername(jwt);
        }
        return username;
    }

    public static String getTokenHeaders(HttpHeaders headers, JwtUtil jwtUtil){
        String authorizationHeader = headers.getFirst("Authorization");
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }
        return jwt;
    }
}
