package com.boot.board_240214.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        //            허용되는 url
//                        .requestMatchers("/", "/home").permitAll()
//                        .requestMatchers("/", "/css/**").permitAll()
//                        .requestMatchers("/", "/css/**","/images/**").permitAll()
                        .requestMatchers("/", "/css/**","/images/**", "/js/**","/account/signup").permitAll()
//                        .requestMatchers("api/delete/").permitAll()
                        .anyRequest().authenticated()
                )
                //        허가되지 않은 경우 url 이동
                .formLogin((form) -> form
//                        .loginPage("/login")
                        .loginPage("/account/signup")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username,password,enabled "
                        + "from user "
                        + "where username = ?")
                .authoritiesByUsernameQuery("select u.username, r.name "
                        + "from user_role ur "
                        + "inner join user u "
                        + "on ur.user_id = u.id "
                        + "inner join role r "
                        + "on ur.role_id = r.id "
                        + "where u.username = ?");
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}