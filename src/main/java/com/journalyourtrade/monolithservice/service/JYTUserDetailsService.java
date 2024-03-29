package com.journalyourtrade.monolithservice.service;

import com.journalyourtrade.monolithservice.exception.UserExistException;
import com.journalyourtrade.monolithservice.model.configuration.JYTUserDetails;
import com.journalyourtrade.monolithservice.model.entity.JYTUser;
import com.journalyourtrade.monolithservice.model.entity.Role;
import com.journalyourtrade.monolithservice.model.entity.Roles;
import com.journalyourtrade.monolithservice.model.web.JYTInternalUserDto;
import com.journalyourtrade.monolithservice.model.web.JYTUserDto;
import com.journalyourtrade.monolithservice.repository.JYTUserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class JYTUserDetailsService implements UserDetailsService {

    JYTUserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public JYTUserDetailsService(JYTUserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<JYTUser> user = userRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        return user.map(JYTUserDetails::new).get();
    }

    public JYTUserDto loadUserDtoByUsername(String userName) throws UsernameNotFoundException {
        Optional<JYTUser> user = userRepository.findByEmail(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));

        JYTUser jytUser = user.get();
        return JYTUserDto.builder()
                .email(jytUser.getEmail())
                .firstName(jytUser.getFirstName())
                .lastName(jytUser.getLastName())
                .roles(jytUser.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList()))
                .build();
    }

    public JYTUser createWebUser(JYTInternalUserDto userDto) throws UserExistException {

        Optional<JYTUser> user = userRepository.findByEmail(userDto.getEmail());

        if (!user.isEmpty()) {
            throw new UserExistException();
        }

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(Roles.ROLE_USER_STR));

        JYTUser jytUser = JYTUser.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .roles(roles)
                .build();
        return userRepository.save(jytUser);
    }

    @Transactional
    public void deleteUserByUsername(String username) throws Exception {
        userRepository.deleteByEmail(username);
    }
}
