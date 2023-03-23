package com.portfolioCRUD.portfolio.security.service;

import com.portfolioCRUD.portfolio.security.entity.MainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return verifyEmail(username) ?
                MainUser.build(userService.getByEmail(username).get())
                :
                MainUser.build(userService.getByUserName(username).get());
    }

    private boolean verifyEmail(String email) {
        return email.contains("@");
    }



}
