package com.portfolioCRUD.portfolio.security.controller;


import com.portfolioCRUD.portfolio.dto.Message;
import com.portfolioCRUD.portfolio.security.dto.*;
import com.portfolioCRUD.portfolio.security.entity.RefreshToken;
import com.portfolioCRUD.portfolio.security.entity.Rol;
import com.portfolioCRUD.portfolio.security.entity.User;
import com.portfolioCRUD.portfolio.security.enums.RolName;
import com.portfolioCRUD.portfolio.security.jwt.JwtProvider;
import com.portfolioCRUD.portfolio.security.service.RefreshTokenService;
import com.portfolioCRUD.portfolio.security.service.RolService;
import com.portfolioCRUD.portfolio.security.service.UserService;
import com.portfolioCRUD.portfolio.security.exception.TokenRefreshException;
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

// Se logea con el username o el email, el token contiene el username
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

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/register")
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
        return ResponseEntity.ok(new Message("Usuario creado"));
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

        User user = this.getUser(loginUser.getUserName());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        UserResponse userResponse = new UserResponse(user.getUserName(), user.getEmail(), user.getId());

        JwtDto jwtDto = new JwtDto(jwt, refreshToken.getToken(), userResponse, userDetails.getAuthorities());

        return ResponseEntity.ok(jwtDto);
    }

    @GetMapping("/logout")
    public ResponseEntity<Message> logout(@RequestParam Long userId) {
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new Message("Logout exitoso"));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtProvider.generateTokenFromUsername(user.getUserName());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token invalido o expirado. Por favor, inicie sesión nuevamente"));
    }

    private User getUser(String username){
        return username.contains("@") ?
                userService.getByEmail(username).get() : userService.getByUserName(username).get();
    }


}
