package com.sxu.job.security.jwt;

import com.sxu.job.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            logger.debug("Processing request to: {}", request.getRequestURI());
            
            String jwt = parseJwt(request);
            logger.debug("Extracted JWT token: {}", jwt != null ? "[token present]" : "null");
            
            if (jwt != null) {
                try {
                    logger.debug("Validating JWT token...");
                    if (jwtUtils.validateJwtToken(jwt)) {
                        String username = jwtUtils.getUserNameFromJwtToken(jwt);
                        logger.debug("JWT token validated for user: {}", username);

                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        logger.debug("Loaded user details with authorities: {}", userDetails.getAuthorities());
                        
                        UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                        
                        logger.debug("Successfully set authentication in security context");
                    } else {
                        logger.warn("JWT token validation failed");
                    }
                } catch (Exception e) {
                    logger.error("Cannot set user authentication: {}", e.getMessage(), e);
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Invalid or expired token");
                    return;
                }
            } else {
                // No token provided but trying to access secured endpoint
                String requestURI = request.getRequestURI();
                if (requestURI.startsWith("/api/me/") || requestURI.startsWith("/api/admin/")) {
                    logger.error("No JWT token found for secured endpoint: {}", requestURI);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized - No token provided");
                    return;
                } else {
                    logger.debug("No JWT token but endpoint doesn't require authentication: {}", requestURI);
                }
            }
        } catch (Exception e) {
            logger.error("Error processing authentication: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing authentication");
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Clear the security context after the request is processed
            SecurityContextHolder.clearContext();
        }
    }

    private String parseJwt(HttpServletRequest request) {
        try {
            // 1. Try to get token from Authorization header
            String headerAuth = request.getHeader("Authorization");
            logger.debug("Authorization header: {}", headerAuth);

            if (StringUtils.hasText(headerAuth)) {
                if (headerAuth.startsWith("Bearer ")) {
                    String token = headerAuth.substring(7).trim();
                    logger.debug("Extracted JWT token from Authorization header");
                    return token;
                } else {
                    logger.warn("Authorization header doesn't start with 'Bearer '");
                }
            }

            // 2. Try to get token from query parameter
            String token = request.getParameter("token");
            if (StringUtils.hasText(token)) {
                logger.debug("Extracted JWT token from query parameter");
                return token.trim();
            }
            
            logger.debug("No JWT token found in request");
            return null;
        } catch (Exception e) {
            logger.error("Error parsing JWT token: {}", e.getMessage(), e);
            return null;
        }
    }
}
