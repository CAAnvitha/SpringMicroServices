package com.anvitha.discoveryserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;


@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfiguration {

//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder){
//
//    }
}
