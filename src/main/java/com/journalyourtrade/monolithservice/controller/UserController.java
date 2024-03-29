package com.journalyourtrade.monolithservice.controller;

import com.journalyourtrade.monolithservice.exception.UserExistException;
import com.journalyourtrade.monolithservice.model.entity.JYTUser;
import com.journalyourtrade.monolithservice.model.web.AuthenticationRequest;
import com.journalyourtrade.monolithservice.model.web.AuthenticationRespone;
import com.journalyourtrade.monolithservice.model.web.JYTInternalUserDto;
import com.journalyourtrade.monolithservice.model.web.JYTUserDto;
import com.journalyourtrade.monolithservice.service.JYTUserDetailsService;
import com.journalyourtrade.monolithservice.util.JwtUtil;
import com.journalyourtrade.monolithservice.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/getUser")
    public ResponseEntity getUser(@RequestHeader HttpHeaders headers) {
        final String username = UserUtil.getUsernameFromHeaders(headers, jwtTokenUtil);
        return new ResponseEntity(userDetailsService.loadUserDtoByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createWebUser(@Validated @RequestBody JYTInternalUserDto userDto) {
        try {
            userDetailsService.createWebUser(userDto);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (UserExistException e) {
            return new ResponseEntity<>("User already exists!", HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteWebUser(@RequestBody AuthenticationRequest deleteRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(deleteRequest.getUsername(), deleteRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userDetailsService.deleteUserByUsername(deleteRequest.getUsername());
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authRequest) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect username or password.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationRespone(jwt));
    }
}
