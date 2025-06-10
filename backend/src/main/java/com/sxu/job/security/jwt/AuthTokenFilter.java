package com.sxu.job.security.jwt;

import com.sxu.job.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
            String requestURI = request.getRequestURI();
            logger.debug("Processing request to: {}", requestURI);
            
            // Skip authentication for public endpoints
            if (isPublicEndpoint(requestURI)) {
                logger.debug("Skipping authentication for public endpoint: {}", requestURI);
                filterChain.doFilter(request, response);
                return;
            }
            
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
                        
                        logger.debug("Successfully set authentication in security context for user: {}", username);
                        
                        // Continue with the filter chain
                        filterChain.doFilter(request, response);
                        return;
                    } else {
                        logger.warn("JWT token validation failed");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Invalid or expired token");
                        return;
                    }
                } catch (Exception e) {
                    logger.error("Cannot set user authentication: {}", e.getMessage(), e);
                    SecurityContextHolder.clearContext();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Invalid or expired token");
                    return;
                }
            } else {
                // No token provided but trying to access secured endpoint
                logger.error("No JWT token found for secured endpoint: {}", requestURI);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized - No token provided");
                return;
            }
        } catch (Exception e) {
            logger.error("Error processing authentication: {}", e.getMessage(), e);
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing authentication");
            return;
        }
    }
    
    private boolean isPublicEndpoint(String requestURI) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return requestURI.startsWith("/api/auth/") || 
               requestURI.startsWith("/api/test/") ||
               requestURI.startsWith("/api/public/") ||
               (requestURI.startsWith("/api/jobs") && "GET".equalsIgnoreCase(request.getMethod()));
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
