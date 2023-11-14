package com.team5.WalkingWithWorld.global.config;

import com.team5.WalkingWithWorld.global.config.auth.CustomPrincipal;
import com.team5.WalkingWithWorld.global.config.auth.CustomUserDetails;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.users.entity.Users;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration
public class JpaConfig {
    private final HttpSession session;

    @Bean
    public AuditorAware<String> auditorAware() {
        //시큐리티 적용
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(CustomPrincipal.class::cast)
                .map(CustomPrincipal::name);


    }

}
