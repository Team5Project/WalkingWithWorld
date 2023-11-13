package com.team5.WalkingWithWorld.global.config;

import com.team5.WalkingWithWorld.global.SessionConst;
import com.team5.WalkingWithWorld.users.dto.UsersDTO;
import com.team5.WalkingWithWorld.users.entity.Users;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration
public class JpaConfig {
    private final HttpSession session;

    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.ofNullable(session.getAttribute(SessionConst.LONGIN_USERS))
                .map(UsersDTO.class::cast)
                .map(UsersDTO::getName);

    }

}
