package com.code.weirdo.CareerConnect.service;

import com.code.weirdo.CareerConnect.models.UserCC;
import com.code.weirdo.CareerConnect.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {
    private final UserRepository repository;

    public CustomUserService(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCC user = repository.findUserByEmail(username);

        if (user == null)
            throw new UsernameNotFoundException("User with " + username + " was not found");

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new User(user.getEmail(), user.getPassword(), authorities);
    }
}
