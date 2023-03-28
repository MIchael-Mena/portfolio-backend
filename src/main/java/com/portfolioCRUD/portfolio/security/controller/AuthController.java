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
import com.portfolioCRUD.portfolio.security.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// Se registra con el username o el email, el token contiene el username
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    final
    PasswordEncoder passwordEncoder;
    final
    AuthenticationManager authenticationManager;
    final
    UserService userService;
    final
    RolService rolService;
    final
    JwtProvider jwtProvider;
    final
    RefreshTokenService refreshTokenService;
    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;
    @Value("${jwt.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UserService userService, RolService rolService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.rolService = rolService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Message("Something went wrong"));
        }
        if (userService.existsByUserName(newUser.getUserName())) {
            return ResponseEntity.badRequest().body(new Message("The username already exists"));
        }
        if (userService.existsByEmail(newUser.getEmail())) {
            return ResponseEntity.badRequest().body(new Message("The email already exists"));
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
        return ResponseEntity.ok(new Message("User created"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(HttpServletResponse response,
                                   @Valid @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Message("Username or password incorrect"));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = this.getUser(loginUser.getUsername());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(),
                user.getEmail(), userDetails.getAuthorities());

        CookieUtil.create(response, accessTokenCookieName, jwt, false, -1, "/");
        CookieUtil.create(response, refreshTokenCookieName, refreshToken.getToken(),
                false, -1, "/auth");

//        JwtDto jwtDto = new JwtDto(jwt, refreshToken.getToken(), userResponse, userDetails.getAuthorities());

        return ResponseEntity.ok( userResponse );
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            Optional<User> user = userService.getByUserName(username);
            // User contiene el password, no se debe devolver
            if (user.isPresent()) {
                UserResponse userResponse = new UserResponse(user.get().getId(), user.get().getUserName(),
                        user.get().getEmail(), userDetails.getAuthorities());
                return ResponseEntity.ok(userResponse);
            } else {
                return ResponseEntity.badRequest().body(new Message("User not found"));
            }
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message("Something went wrong"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Message> logout(HttpServletResponse response,
                                          HttpServletRequest request) {
//        @CookieValue(name = "refreshToken") String requestRefreshToken
        try {
            String requestRefreshToken = WebUtils.getCookie(request, refreshTokenCookieName).getValue();
            refreshTokenService.deleteByToken(requestRefreshToken);
            CookieUtil.clear(response, accessTokenCookieName);
            CookieUtil.clear(response, refreshTokenCookieName);

            return ResponseEntity.ok(new Message("Logout successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Message("Something went wrong"));
        }
    }

/*    @GetMapping("/logout")
    public ResponseEntity<Message> logout(@RequestParam Long userId) {
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new Message("Logout successful"));
    }*/

    @GetMapping("/refresh-token")
    public ResponseEntity<Message> refreshToken(HttpServletResponse response, HttpServletRequest request) {
        String requestRefreshToken = WebUtils.getCookie(request, refreshTokenCookieName).getValue();
        if(requestRefreshToken == null) {
            return ResponseEntity.badRequest().body(new Message("Refresh token is missing"));
        }
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    System.out.println("userId: " + user.getId());
                    String token = jwtProvider.generateTokenFromUsername(user.getUserName());

                    CookieUtil.create(response, accessTokenCookieName, token,
                            false, -1, "/");
                    return ResponseEntity.ok(new Message("Token refreshed successfully"));
                })
                .orElseThrow(() -> new TokenRefreshException("Refresh token",
                        "Invalid or expired. Please issue a new login request"));
    }

/*    @PostMapping("/refreshToken")
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
                        "Refresh token invalid or expired. Please issue a new request"));
    }*/

    private User getUser(String username){
        return username.contains("@") ?
                userService.getByEmail(username).get() : userService.getByUserName(username).get();
    }


}
