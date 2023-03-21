package com.portfolioCRUD.portfolio.security.service;

import com.portfolioCRUD.portfolio.security.entity.MainUser;
import com.portfolioCRUD.portfolio.security.entity.User;
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
        User user = userService.getByUserName(username).get();
//        User user = userService.getByEmail(username).get();
        return MainUser.build(user);
    }



}
