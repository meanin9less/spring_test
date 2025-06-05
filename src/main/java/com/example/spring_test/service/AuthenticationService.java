package com.example.spring_test.service;

import com.example.spring_test.data.dao.AuthenticationDAO;
import com.example.spring_test.data.dto.AuthenticationDTO;
import com.example.spring_test.data.entity.AuthenticationEntity;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final AuthenticationDAO authenticationDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        String loginRole = "";
        String username2 = "";
        String[] split = username.split("@");
        if(split.length==2){
            loginRole = "ROLE_"+split[0];
            username2 = split[1];
        }

        AuthenticationEntity authenticationEntity = this.authenticationDAO.findById(username2);
        if(authenticationEntity==null){
            throw new UsernameNotFoundException("not found");
        }

        if(loginRole.equals("ROLE_ADMIN") && !authenticationEntity.getRole().equals(loginRole)){
            throw new AuthorizationDeniedException("not admin \nusername: " + username2 + " / role : "+authenticationEntity.getRole().substring(5));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(); // GrantedAutority 롤 정보를 담는 인터페이스

        if(loginRole.equals("ROLE_USER")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        if(loginRole.equals("ROLE_ADMIN")){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(authenticationEntity.getUsername(), authenticationEntity.getPassword(), grantedAuthorities);
    }

    public void join(AuthenticationDTO authenticationDTO){
        String joinRole = "";
        String username2 = "";
        String[] split = authenticationDTO.getUsername().split("@");
        if(split.length==2){
            joinRole = "ROLE_"+split[0];
            username2 = split[1];
        }
        if(this.authenticationDAO.isExist(username2)){
            throw new EntityExistsException("username already exists");
        }
        this.authenticationDAO.join(username2, authenticationDTO.getPassword(), joinRole, authenticationDTO.getName());
    }

}
