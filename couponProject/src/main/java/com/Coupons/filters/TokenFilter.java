package com.Coupons.filters;

import com.Coupons.Entities.MySession;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
@Order(2)
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    HashMap<String, MySession> sessions;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            if (token != null) {
                String clientType = getClientTypeFromToken(token);
                if (clientType != null && isClientTypeAllowed(clientType, request.getServletPath())) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized, please log in!");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String servletPath = request.getServletPath();
        return servletPath.startsWith("/auth") || servletPath.equals("/admin/getAllCompanies")||servletPath.startsWith("/allCoupons");
    }



    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    private String getClientTypeFromToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            Claim clientTypeClaim = decodedJWT.getClaim("clientType");
            if (clientTypeClaim != null) {
                return clientTypeClaim.asString();
            }
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isClientTypeAllowed(String clientType, String servletPath) {
        if (clientType.equalsIgnoreCase("Admin") && servletPath.startsWith("/admin")) {
            return true;
        } else if (clientType.equalsIgnoreCase("Company") && servletPath.startsWith("/company")) {
            return true;
        } else if (clientType.equalsIgnoreCase("Customer") && servletPath.startsWith("/customer")) {
            return true;
        }
        return false;
    }
}

