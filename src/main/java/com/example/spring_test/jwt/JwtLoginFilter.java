package com.example.spring_test.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager; // 직접구현한 로그인필터여서 인증매니저가 필요함

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        if (username == null || password == null) {
            throw new AuthenticationServiceException("Username and password cannot be empty");
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                         Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal(); // getPrincipal 리턴값 : Object

        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities(); //
        Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("username", username);
        responseData.put("role", role);
        responseData.put("result", "success");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(responseData);

        String access = this.jwtUtil.createToken("access", username, role, 5000L ); // L > Long
        String refresh = this.jwtUtil.createToken("refresh", username, role, 60*60*24*1000L );

        response.addHeader("Authorization", "Bearer " + access);
        response.addCookie(this.createCookie("refresh", refresh));

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonMessage);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("result", failed.getMessage());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(responseData);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(jsonMessage);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/"); //브라우저에 저장될 쿠키가 프론트에서 보내는 모든 url경로의 요청에 포함되게 하겠다.
        cookie.setHttpOnly(true); // 브라우저에서 , 프론트에서 사용할 수 없음 withcredential true면 그냥 포함되게만 함
        cookie.setMaxAge(60*60*24); // 밀리초 x 초단위
        return cookie;
    }

}
