package com.portfolioCRUD.portfolio.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    @Value("${server.domain}")
    private static String domain;

    public static void create(HttpServletResponse res, String name, String value, int maxAge, String path) {
        // Cookie debe ser secure = true (https), si no chrome no guarda el cookie.
        // Igualmente chrome en modo incognito no guarda el cookie.
        // Si estoy en localhost cambiar a false.
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setAttribute("SameSite", "None");
        res.addCookie(cookie);
    }

    public static void clear(HttpServletResponse res, String name) {
        create(res, name, "", 0, "/");
    }

}
