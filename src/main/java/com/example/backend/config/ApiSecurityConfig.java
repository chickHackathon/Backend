/*package com.example.backend.config;

import com.example.backend.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.POST,"/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/*").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/*").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/*").permitAll()
                        .requestMatchers("/swagger-ui/index.html").permitAll()
                        .requestMatchers("/member/*").permitAll() // 로그인은 누구나 가능, post 요청만 허용
                        .anyRequest().authenticated()
                )
                .csrf(CsrfConfigurer::disable) // csrf 토큰 끄기
                .httpBasic(HttpBasicConfigurer::disable) // httpBasic 로그인 방식 끄기
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 방식 끄기
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtAuthorizationFilter, //엑세스 토큰을 이용한 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}

 */