package com.sampleai.order.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("GET","/api/orders/**").permitAll()
            .antMatchers("POST","/api/orders").hasRole("USER")
            .antMatchers("PATCH","/api/orders/*/status").hasRole("ADMIN")
            .anyRequest().authenticated();
    }
}
