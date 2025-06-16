package com.sxu.job.security;

import com.sxu.job.security.jwt.AuthEntryPointJwt;
import com.sxu.job.security.jwt.AuthTokenFilter;
import com.sxu.job.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. 应用我们下面定义的 CORS 配置
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. 禁用 CSRF，这是解决之前 403 问题的关键
            .csrf(csrf -> csrf.disable())
            
            // 3. 配置异常处理，指定未经授权的入口点
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            
            // 4. 设置会话管理为无状态，因为我们使用 JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. 定义请求授权规则
            .authorizeHttpRequests(auth -> {
                // 公共访问的端点
                auth.requestMatchers("/api/auth/**").permitAll()
                   .requestMatchers("/api/test/**").permitAll()
                   .requestMatchers("/api/public/**").permitAll()
                   .requestMatchers("/v3/api-docs/**").permitAll()
                   .requestMatchers("/swagger-ui/**").permitAll()
                   .requestMatchers("/swagger**").permitAll()
                   .requestMatchers("/swagger-resources/**").permitAll()
                   .requestMatchers("/swagger-ui.html").permitAll()
                   .requestMatchers("/webjars/**").permitAll()
                   .requestMatchers(HttpMethod.GET, "/api/jobs/**").permitAll();
                
                // 需要认证的端点
                auth.requestMatchers("/api/me/**").authenticated();
                
                // 需要特定角色的端点
                auth.requestMatchers("/api/me/saved-jobs").hasAnyRole("STUDENT", "ADMIN");
                
                // 其他所有请求都需要认证
                auth.anyRequest().authenticated();
            })
            
            // 6. 添加自定义的认证提供者和 JWT 过滤器
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            
        // 启用调试日志
        http.headers(headers -> headers.cacheControl(cache -> cache.disable()));
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // 7. ✅ 明确允许你的前端源，这是最关键的一步！
        // 允许常见的前端开发服务器端口
        configuration.setAllowedOrigins(List.of(
            "http://localhost:5173", 
            "http://127.0.0.1:5173",
            "http://localhost:3000",
            "http://127.0.0.1:3000"
        ));
        
        // 8. 允许所有常用的 HTTP 方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        
        // 9. 允许所有你需要的请求头，特别是 Authorization 和 Content-Type
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With", "Origin"));
        
        // 10. ✅ 必须设置为 true，以响应前端 withCredentials: true 的配置
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有 API 路径应用此 CORS 配置
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}