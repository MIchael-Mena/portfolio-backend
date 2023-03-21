package com.portfolioCRUD.portfolio.security.controller;


import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.security.dto.JwtDto;
import com.portfolioCRUD.portfolio.security.dto.LoginUser;
import com.portfolioCRUD.portfolio.security.dto.NewUser;
import com.portfolioCRUD.portfolio.security.entity.Rol;
import com.portfolioCRUD.portfolio.security.entity.User;
import com.portfolioCRUD.portfolio.security.enums.RolName;
import com.portfolioCRUD.portfolio.security.jwt.JwtProvider;
import com.portfolioCRUD.portfolio.security.service.RolService;
import com.portfolioCRUD.portfolio.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/new")
    public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Message("Campos o email incorrectos"));
        }
        if (userService.existsByUserName(newUser.getUserName())) {
            return ResponseEntity.badRequest().body(new Message("El nombre de usuario ya existe"));
        }
        if (userService.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body(new Message("El email ya existe"));
        }
        User user = new User(newUser.getName(), newUser.getUserName(), newUser.getEmail(),
                passwordEncoder.encode(newUser.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(rolService.getByRolName(RolName.ROLE_USER).get());
        if (newUser.getRoles().contains("admin")) {
            roles.add(rolService.getByRolName(RolName.ROLE_ADMIN).get());
        }
        user.setRoles(roles);
        userService.save(user);
        return ResponseEntity.created(null).body(new Message("Usuario creado"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Message("Campos incorrectos"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return ResponseEntity.ok(jwtDto);
    }


}
