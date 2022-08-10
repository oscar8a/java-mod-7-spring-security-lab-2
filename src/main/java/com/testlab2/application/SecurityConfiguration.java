package com.testlab2.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/api/status")
                .hasAuthority("admin");

        httpSecurity.authorizeRequests()
                .antMatchers("/api/**")
                .authenticated()
                .and()
                .formLogin()
                .and()
                .logout();

        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login();

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailServiceManager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("test")
                .password(passwordEncoder().encode("test"))
                .authorities("read")
                .build();

        UserDetails adminUser1 = User.withUsername("admin")
                .password(passwordEncoder().encode("test"))
                .authorities("admin")
                .build();

        userDetailServiceManager.createUser(user1);
        userDetailServiceManager.createUser(adminUser1);

        return userDetailServiceManager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
