package com.journalyourtrade.monolithservice.controller;

import com.journalyourtrade.monolithservice.exception.UserExistException;
import com.journalyourtrade.monolithservice.model.web.AuthenticationRequest;
import com.journalyourtrade.monolithservice.model.web.AuthenticationRespone;
import com.journalyourtrade.monolithservice.model.web.JYTUserDto;
import com.journalyourtrade.monolithservice.service.JYTUserDetailsService;
import com.journalyourtrade.monolithservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private AuthenticationManager authenticationManager;
    private JYTUserDetailsService userDetailsService;
    private JwtUtil jwtTokenUtil;

    @GetMapping("/")
    public ResponseEntity getPublic() {
        return new ResponseEntity("hello public", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity getUser() {
        return new ResponseEntity("hello user", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity getAdmin() {
        return new ResponseEntity("hello admin", HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authRequest) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationRespone(jwt));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWebUser(@Validated @RequestBody JYTUserDto userDto) {
        try {
            userDetailsService.createWebUser(userDto);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (UserExistException e) {
            return new ResponseEntity<>("User already exists!", HttpStatus.FORBIDDEN);
        }
    }
}
