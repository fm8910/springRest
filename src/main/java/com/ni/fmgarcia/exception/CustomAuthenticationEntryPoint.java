package com.ni.fmgarcia.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ni.fmgarcia.model.dto.HandleMessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Esta clase se encarga de manejar las excepciones de autenticación de Spring Security,
 * ya que el controler advice no puede manejarlas.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException) throws IOException {

        HandleMessageResponse apiError = new HandleMessageResponse(
                "Acceso denegado");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiError));
    }


}
