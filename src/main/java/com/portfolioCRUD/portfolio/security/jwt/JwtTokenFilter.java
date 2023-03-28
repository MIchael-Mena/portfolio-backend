package com.portfolioCRUD.portfolio.security.jwt;

import com.portfolioCRUD.portfolio.security.service.UserDetailsServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailsServiceImp userDetailsService;
    @Value("${jwt.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = this.getToken(req);
            if(token != null && this.jwtProvider.validateToken(token)){
                String username = this.jwtProvider.getUserNameFromToken(token);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error("Fail en el método doFilterInternal" + e.getMessage());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
        filterChain.doFilter(req, res);
    }

    private String getToken(HttpServletRequest req) {
        Cookie cookie = WebUtils.getCookie(req, this.accessTokenCookieName);
        return cookie != null ? cookie.getValue() : null;
/*        String header = req.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;*/
    }

}
