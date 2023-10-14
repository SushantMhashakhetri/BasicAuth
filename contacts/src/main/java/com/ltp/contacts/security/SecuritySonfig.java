package com.ltp.contacts.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecuritySonfig {

    private BCryptPasswordEncoder bcriBCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
        .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN","USER")
        .antMatchers(HttpMethod.GET).permitAll()
        .anyRequest()
        .authenticated()
        .and().httpBasic()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails admin =  User.builder().username("admin").password(bcriBCryptPasswordEncoder.encode("admin")).roles("ADMIN").build();
        UserDetails user =  User.builder().username("user").password(bcriBCryptPasswordEncoder.encode("user")).roles("USER").build();
        return new InMemoryUserDetailsManager(admin,user);
    }
    
}
