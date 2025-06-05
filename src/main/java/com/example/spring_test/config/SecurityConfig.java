package com.example.spring_test.config;

import com.example.spring_test.component.CustomAuthenticationEntryPoint;
import com.example.spring_test.component.CustomerAccessDeniedHandler;
import com.example.spring_test.jwt.JwtFilter;
import com.example.spring_test.jwt.JwtLoginFilter;
import com.example.spring_test.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomerAccessDeniedHandler customerAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .formLogin(form ->form.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(authorize->{
                    authorize.requestMatchers("/", "/login", "/join", "/board/postlist", "/reissue").permitAll();
                    authorize.requestMatchers("/admin/**").hasRole("ADMIN");
                    authorize.anyRequest().authenticated();
                })
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOrigin("http://localhost:3000");
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.setExposedHeaders(List.of("Authorization"));// 헤더를 읽을 수 있도록 허용
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.setAllowCredentials(true); // 헤더를 읽을 수 있도록 허용
                    return corsConfiguration;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(this.jwtUtil), JwtLoginFilter.class)
                .addFilterAt(new JwtLoginFilter(this.jwtUtil, this.authenticationManager(this.authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class) //addfilter > 마지막에 ,
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(this.customAuthenticationEntryPoint);
                    exception.accessDeniedHandler(this.customerAccessDeniedHandler);
                });
        return http.build();
    }
}

