package com.db.taskcrud.infra.security;

import com.db.taskcrud.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final PersonRepository personRepository;
    private final SecurityConfiguration securityConfiguration;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Person not found"));
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(securityConfiguration.passwordEncoder());
        return authProvider;
    }*/
}
