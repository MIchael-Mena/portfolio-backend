package com.portfolioCRUD.portfolio.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // logger solo se usa en desarrollo para ver errores en consola
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
/*        logger.error("Fail en el método commence");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");*/
        // El método doFilterInternal de JwtTokenFilter se encarga de enviar el mensaje de error relacionado con el token.
        // Si un recurso requiere autenticación y hay algún error en el token (JwtTokenFilter asignará el error en él response)
        // se enviará el 401 Unauthorized por encima de cualquier otro error (por ejemplo un json mal formado o un recurso no encontrado).
        if(response.getStatus() == HttpServletResponse.SC_OK){
            // Si el usuario no está autenticado y quiere acceder a un recurso protegido
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        logger.error("Fail en el método commence");
    }

}
