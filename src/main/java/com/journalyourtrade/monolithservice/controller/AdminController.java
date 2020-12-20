package com.journalyourtrade.monolithservice.controller;

import com.journalyourtrade.monolithservice.service.JYTUserDetailsService;
import com.journalyourtrade.monolithservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private AuthenticationManager authenticationManager;
    private JYTUserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;

    @GetMapping("/")
    public ResponseEntity getAdmin() {
        return new ResponseEntity("hello admin", HttpStatus.OK);
    }

}
