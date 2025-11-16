package com.example.poverty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 开发阶段关闭 CSRF
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll() // 登录注册公开
                .anyRequest().authenticated()               // 其他接口需要 JWT
                .and()
                .formLogin().disable() // 禁掉默认登录表单
                .httpBasic().disable(); // 禁掉 HTTP Basic 弹窗

        return http.build();
    }
}
