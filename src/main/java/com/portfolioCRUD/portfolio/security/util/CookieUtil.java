package com.portfolioCRUD.portfolio.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    public static void create(HttpServletResponse res, String name, String value, boolean secure, int maxAge, String domain, String path) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        res.addCookie(cookie);
    }

    public static void clear(HttpServletResponse res, String name) {
        create(res, name, "", false, 0, "localhost", "/");
    }

}
