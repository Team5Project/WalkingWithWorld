package com.team5.WalkingWithWorld.global.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.team5.WalkingWithWorld.global.config.auth.CustomAuthorityUtils;
import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.global.config.auth.CustomUserDetails;
import com.team5.WalkingWithWorld.global.exception.BusinessLogicException;
import com.team5.WalkingWithWorld.global.exception.ExceptionCode;
import com.team5.WalkingWithWorld.users.entity.Users;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UsersRepository usersRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UsersRepository usersRepository,CustomAuthorityUtils customAuthorityUtils) {
        super(authenticationManager);
        this.usersRepository = usersRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("오조의마법사")).build().verify(token)
                .getClaim("email").asString();

        if(email!=null){
            Users users = usersRepository.findUsersByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
            CustomUserDetails principal = new CustomUserDetails(users);
            List<GrantedAuthority> authorities = customAuthorityUtils.createAuthorities(principal.getUsername());
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(CustomPrincipal.of(users.getId(),users.getEmail(),users.getName()), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }
}
