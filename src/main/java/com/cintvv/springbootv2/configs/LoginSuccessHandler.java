package com.cintvv.springbootv2.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        String role = authentication.getAuthorities().toString();
        if(role.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(httpServletRequest.getContextPath() + "/admin"));
        }
        else if(role.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(httpServletRequest.getContextPath() + "/user"));
        }
    }
}