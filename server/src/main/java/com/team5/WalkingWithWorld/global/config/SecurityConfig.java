package com.team5.WalkingWithWorld.global.config;

import com.team5.WalkingWithWorld.global.config.auth.CustomAuthorityUtils;
import com.team5.WalkingWithWorld.global.config.jwt.JwtAuthenticationFilter;
import com.team5.WalkingWithWorld.global.config.jwt.JwtAuthorizationFilter;
import com.team5.WalkingWithWorld.users.repository.UsersRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UsersRepository usersRepository;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final CorsConfig corsConfig;

    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(UsersRepository usersRepository,
                          CustomAuthorityUtils customAuthorityUtils,
                          CorsConfig corsConfig,
                          AuthenticationConfiguration authenticationConfiguration) {
        this.usersRepository = usersRepository;
        this.customAuthorityUtils = customAuthorityUtils;
        this.corsConfig = corsConfig;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(jwtAuthorizationFilter())
                .authorizeRequests()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().permitAll();

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        return new JwtAuthenticationFilter(authenticationManager());
    }
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception{
        return new JwtAuthorizationFilter(authenticationManager(), usersRepository,customAuthorityUtils);
    }
}
